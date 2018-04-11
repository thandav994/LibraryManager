package com.main.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.main.beans.Book;
import com.main.beans.BookLoan;
import com.main.beans.Borrower;
import com.main.beans.Fine;
import com.main.entities.BookEntity;
import com.main.entities.BookLoansEntity;
import com.main.entities.BorrowerEntity;
import com.main.entities.FinesEntity;
import com.main.exception.SSNAlreadyPresentException;
import com.main.exception.TooManyBooksException;

public class LibraryDAO {
	SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml").buildSessionFactory();
	
	public ArrayList<Book> getBooksList(String query) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		ArrayList<Book> books = new ArrayList<>();
		String sql = "SELECT  a.isbn," + 
				"        a.title," + 
				"        GROUP_CONCAT(c.Name ORDER BY c.Name) Authors" + 
				"		 FROM Book a" + 
				"        INNER JOIN book_authors b" + 
				"            ON a.isbn = b.isbn" + 
				"        INNER JOIN authors c" + 
				"            ON b.author_id = c.author_id "
				+ "where a.isbn like :query or a.title like :query or c.name like :query "
				+ "GROUP BY a.isbn, a.title";
		Query<?> sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setParameter("query", "%"+query+"%");
		List<?> list = sqlQuery.getResultList();
		
		for(int i=0; i<list.size(); i++) {
		    Object[] row = (Object[]) list.get(i);
		    Book book = new Book();
		    book.setIsbn((String) row[0]);
		    book.setName((String) row[1]);
		    book.setAuthors((String) row[2]);
		    sql = "select * from book_loans where isbn = :param";
		    sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter("param", book.getIsbn());
			if(sqlQuery.getResultList().isEmpty()) {
				book.setAvailability("Available");
			} else {
				sql = "select * from book_loans where isbn = :param and date_in is null";
			    sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setParameter("param", book.getIsbn());
				if(sqlQuery.getResultList().isEmpty()) {
					book.setAvailability("Available");
				}
				else {
					book.setAvailability("Not Available");
				}
			}
		    books.add(book);
		}  
		
		session.close();
		return books;
		
	}

	public void checkOut(String isbn, Integer card_id) throws TooManyBooksException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Checking if the borrower already has 3 book loans
		Query<BookLoansEntity> query = session.createQuery("from BookLoansEntity b where b.borrower.card_id =:param and b.date_in is null");
		query.setParameter("param", card_id);
		if(query.getResultList().size() >= 3) {
			throw new TooManyBooksException();
		}
		BookLoansEntity bookloan = new BookLoansEntity();
		bookloan.setBook(session.load(BookEntity.class, isbn));
		bookloan.setBorrower(session.load(BorrowerEntity.class, card_id));
		bookloan.setDate_out(Calendar.getInstance().getTime());
		Calendar dueDate = Calendar.getInstance();
		dueDate.add(Calendar.DATE, 14);
		bookloan.setDue_date(dueDate.getTime());
		session.save(bookloan);
		session.getTransaction().commit();
		session.close();
	}

	public void addBorrower(Borrower borrower) throws SSNAlreadyPresentException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<BorrowerEntity> query = session.createQuery("from BorrowerEntity b where b.ssn = :param");
		query.setParameter("param", borrower.getSsn());
		if(!query.getResultList().isEmpty())
			throw new SSNAlreadyPresentException();
		BorrowerEntity borrowerEntity = new BorrowerEntity();
		borrowerEntity.setBname(borrower.getName());
		borrowerEntity.setAddress(borrower.getAddress()+","+borrower.getCity()+","+borrower.getState());
		borrowerEntity.setPhone(borrower.getPhone());
		borrowerEntity.setSsn(borrower.getSsn());
		session.save(borrowerEntity);
		session.getTransaction().commit();
		session.close();
	}

	public List<BookLoan> fetchBooksForCheckIn(String query) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<BookLoansEntity> bookloanQuery = session.createQuery("from BookLoansEntity b where b.date_in is NULL and (b.book.isbn = :param or b.borrower.card_id = :param or b.borrower.bname like :param2)");
		bookloanQuery.setParameter("param", query);
		bookloanQuery.setParameter("param2", "%"+query+"%");
		List<BookLoansEntity> bookloanEntities = bookloanQuery.getResultList();
		List<BookLoan> loans = new ArrayList<>();
		for(BookLoansEntity loanEntity : bookloanEntities) {
			BookLoan loan = new BookLoan();
			loan.setIsbn(loanEntity.getBook().getIsbn());
			loan.setCard_id(loanEntity.getBorrower().getCard_id());
			loan.setLoan_id(loanEntity.getLoan_id());
			loan.setDate_out(loanEntity.getDate_out());
			loan.setDue_date(loanEntity.getDue_date());
			loan.setBorrowerName(loanEntity.getBorrower().getBname());
			loans.add(loan);
		}
		session.close();
		return loans;
	}
	
	public void checkIn(Integer loan_id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		BookLoansEntity bookLoan = session.load(BookLoansEntity.class, loan_id);
		bookLoan.setDate_in(Calendar.getInstance().getTime());
		session.saveOrUpdate(bookLoan);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateFines() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<BookLoansEntity> query = session.createQuery("from BookLoansEntity b where (b.date_in is not null and b.date_in > due_date) or (b.date_in is null and CURDATE() > b.due_date)");
		List<BookLoansEntity> bookloans = query.getResultList();
		for(BookLoansEntity loan : bookloans) {
			if(session.get(FinesEntity.class, loan.getLoan_id()) == null) {
				FinesEntity fine = new FinesEntity();
				fine.setLoan(loan);
				if(loan.getDate_in() == null)
					fine.setFine_amt(0.25 * ((int)( (Calendar.getInstance().getTime().getTime() - loan.getDue_date().getTime()) / (1000 * 60 * 60 * 24))));
				else fine.setFine_amt(0.25 * ((int)((loan.getDate_in().getTime() - loan.getDue_date().getTime()) / (1000 * 60 * 60 * 24))));
				fine.setPaid(false);
				fine.setLoan_id(loan.getLoan_id());
				session.save(fine);
			} else {
				FinesEntity fine = session.get(FinesEntity.class, loan.getLoan_id());
				if(!fine.isPaid()) {
					if(loan.getDate_in() == null)
						fine.setFine_amt(0.25 * ((int)( (Calendar.getInstance().getTime().getTime() - loan.getDue_date().getTime()) / (1000 * 60 * 60 * 24))));
					else fine.setFine_amt(0.25 * ((int)((loan.getDate_in().getTime() - loan.getDue_date().getTime()) / (1000 * 60 * 60 * 24))));
				session.update(fine);
				}
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Fine> getFines() {
		updateFines();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Fine> fines = new ArrayList<>();
		List<FinesEntity> fineEntities = session.createQuery("from FinesEntity").getResultList();
		for(FinesEntity fineEntity : fineEntities) {
			Fine fine = new Fine();
			fine.setFine_amt(fineEntity.getFine_amt());
			Borrower borrower = new Borrower();
			borrower.setName(fineEntity.getLoan().getBorrower().getBname());
			borrower.setCard_id(fineEntity.getLoan().getBorrower().getCard_id());
			fine.setLoan_id(fineEntity.getLoan_id());
			fine.setPaid(fineEntity.isPaid());
			fine.setBorrower(borrower);
			fine.setCheckedIn(fineEntity.getLoan().getDate_in() == null ? false: true);
			fines.add(fine);
		}
		session.getTransaction().commit();
		session.close();
		return fines;
	}

	public List<Fine> getFinesByBorrower() {
		// TODO Auto-generated method stub
		updateFines();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Fine> fines = new ArrayList<>();
		List<?> finesList = session.createQuery("select f.loan.borrower, sum(f.fine_amt) from FinesEntity f where f.loan.date_in is not null and f.paid = false group by f.loan.borrower").getResultList();
		for(int i=0; i<finesList.size(); i++) {
		    Object[] row = (Object[]) finesList.get(i);
		    Fine fine = new Fine();
		    Borrower borrower = new Borrower();
		    BorrowerEntity borrowerEntity = (BorrowerEntity) row[0];
		    borrower.setCard_id(borrowerEntity.getCard_id());
		    borrower.setName(borrowerEntity.getBname());
		    System.out.println(borrower.getName());
		    fine.setBorrower(borrower);
		    fine.setFine_amt((Double) row[1]);
		    fines.add(fine);
		}
		session.getTransaction().commit();
		session.close();
		return fines;
	}
	
	public void makePayment(Integer card_id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<FinesEntity> fines = session.createQuery("from FinesEntity f where f.loan.borrower.card_id = :card_id and f.loan.date_in is not null").setParameter("card_id", card_id).getResultList();
		for(FinesEntity fine : fines) {
			fine.setPaid(true);
			session.update(fine);
		}
		session.getTransaction().commit();
		session.close();
	}
}
