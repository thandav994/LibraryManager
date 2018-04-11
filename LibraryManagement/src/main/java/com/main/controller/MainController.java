package com.main.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.main.beans.Book;
import com.main.beans.BookLoan;
import com.main.beans.Borrower;
import com.main.beans.Fine;
import com.main.dao.LibraryDAO;
import com.main.exception.SSNAlreadyPresentException;
import com.main.exception.TooManyBooksException;

@Controller
public class MainController {
	LibraryDAO libraryDao= new LibraryDAO();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
		ModelAndView map = new ModelAndView("Search");
		map.addObject("visible", "hidden");
		return map;
    }
	
	@RequestMapping(value = "/Search", method = RequestMethod.GET)
    public ModelAndView search() {
		ModelAndView map = new ModelAndView("Search");
		map.addObject("visible", "hidden");
		return map;
    }

	@RequestMapping(value = { "/checkout"}, method = RequestMethod.GET)
	public ModelAndView checkOut() {
		ModelAndView map = new ModelAndView("Search");
		map.addObject("visible", "hidden");
	    return map;
	}
	
	@RequestMapping(value = { "/checkin"}, method = RequestMethod.GET)
	public ModelAndView checkin() {
		ModelAndView map = new ModelAndView("CheckIn");
		map.addObject("visible", "hidden");
	    return map;
	}
	
	@RequestMapping(value = "/manageborrowers", method = RequestMethod.GET)
    public ModelAndView manageBorrowers() {
		ModelAndView map = new ModelAndView("Borrower");
		return map;
    }
	
	@RequestMapping(value = "/fetchBooksForCheckIn", method = RequestMethod.GET)
    public ModelAndView fetchBooksForCheckIn() {
		ModelAndView map = new ModelAndView("CheckIn");
		map.addObject("visible", "hidden");
		return map;
    }
	
	@RequestMapping(value = { "/Fines"}, method = RequestMethod.GET)
	public ModelAndView fines() {
		ModelAndView map = new ModelAndView("Fines");
		List<Fine> fines =  libraryDao.getFines();
		List<Fine> borrowerFines =  libraryDao.getFinesByBorrower();
		map.addObject("fines", fines);
		map.addObject("borrowerFines", borrowerFines);
	    return map;
	}
	
	@RequestMapping(value = { "/makePayment"}, method = RequestMethod.GET)
	public ModelAndView makePayment() {
		ModelAndView map = new ModelAndView("Fines");
		List<Fine> fines =  libraryDao.getFines();
		List<Fine> borrowerFines =  libraryDao.getFinesByBorrower();
		map.addObject("fines", fines);
		map.addObject("borrowerFines", borrowerFines);
	    return map;
	}
	
	@RequestMapping(value = { "/Search"}, method = RequestMethod.POST)
	public ModelAndView getBooksList(@RequestParam("query") String query) {    
		ArrayList<Book> books = null;
		books = libraryDao.getBooksList(query);
	    ModelAndView map = new ModelAndView("Search");
	    map.addObject("books", books);
	    if(books.size() == 0) {
	    	map.addObject("errorMessage",getErrorMessage("There are no books found with the given criteria"));
	    	map.addObject("visible", "hidden");
	    } else {
	    	map.addObject("visible", "visible");
	    }
	    return map;
	}
	
	@RequestMapping(value = { "/checkout"}, method = RequestMethod.POST)
	public ModelAndView checkOut(@RequestParam("isbn") String isbn, @RequestParam("card_id") Integer card_id ) {    
		ModelAndView map = new ModelAndView("Search");
		try {
			libraryDao.checkOut(isbn, card_id);
			map.addObject("successMessage", getSuccessMessage("Book checked out successfully."));
			map.addObject("visible", "hidden");
		} catch(TooManyBooksException e) {
			map.addObject("errorMessage", getErrorMessage(e.getMessage()));
			map.addObject("visible", "hidden");
		} catch(Exception e) {
			e.printStackTrace();
			map.addObject("errorMessage", getErrorMessage("Some error occured. Please try again later"));
			map.addObject("visible", "hidden");
		}
	    return map;
	}
	
	@RequestMapping(value = { "/fetchBooksForCheckIn"}, method = RequestMethod.POST)
	public ModelAndView fetchBooksForCheckIn(@RequestParam("query") String query) {    
		ModelAndView map = new ModelAndView("CheckIn");
		try {
			List<BookLoan> loans = libraryDao.fetchBooksForCheckIn(query);
			if(loans.size() == 0) {
		    	map.addObject("errorMessage",getErrorMessage("There are no loans found with the given criteria"));
		    	map.addObject("visible", "hidden");
		    } else {
		    	map.addObject("loans",loans);
		    	map.addObject("visible", "visible");
		    }
		} catch(Exception e) {
			e.printStackTrace();
			map.addObject("errorMessage", getErrorMessage("Some error occured. Please try again later"));
			map.addObject("visible", "hidden");
		}
	    return map;
	}
	
	@RequestMapping(value = { "/checkin"}, method = RequestMethod.POST)
	public ModelAndView checkin(@RequestParam("loan_id") Integer loan_id ) {    
		ModelAndView map = new ModelAndView("CheckIn");
		try {
			libraryDao.checkIn(loan_id);
			map.addObject("successMessage", getSuccessMessage("Book checked in successfully."));
			map.addObject("visible", "hidden");
		} catch(Exception e) {
			e.printStackTrace();
			map.addObject("errorMessage", getErrorMessage("Some error occured. Please try again later"));
			map.addObject("visible", "hidden");
		}
	    return map;
	}
	
	@RequestMapping(value = { "/manageborrowers"}, method = RequestMethod.POST)
	public ModelAndView addBorrower(@ModelAttribute("borrower") Borrower borrower) {    
		ModelAndView map = new ModelAndView("Borrower");
		try {
			libraryDao.addBorrower(borrower);
			map.addObject("successMessage", getSuccessMessage("Borrower added successfully."));
		} catch(SSNAlreadyPresentException e) {
			map.addObject("errorMessage", getErrorMessage(e.getMessage()));
		} catch(Exception e) {
			e.printStackTrace();
			map.addObject("errorMessage", getErrorMessage("Some error occured. Please try again later"));
		}
	    return map;
	}
	
	@RequestMapping(value = { "/makePayment"}, method = RequestMethod.POST)
	public ModelAndView makePayment(@RequestParam("card_id") Integer card_id ) {    
		ModelAndView map = new ModelAndView("Fines");
		try {
			libraryDao.makePayment(card_id);
			map.addObject("successMessage", getSuccessMessage("Payment successful."));
			map.addObject("visible", "visible");
		} catch(Exception e) {
			e.printStackTrace();
			map.addObject("errorMessage", getErrorMessage("Some error occured. Please try again later"));
			map.addObject("visible", "hidden");
		}
		List<Fine> fines =  libraryDao.getFines();
		List<Fine> borrowerFines =  libraryDao.getFinesByBorrower();
		map.addObject("fines", fines);
		map.addObject("borrowerFines", borrowerFines);
	    return map;
	}
	
	private String getErrorMessage(String error) {
		return "<div class=\"alert alert-danger text-center\" role=\"alert\">\r\n" + 
    			"  <strong>Oh snap!</strong> "+error+"\r\n" + 
    			"</div>";
	}
	
	private String getSuccessMessage(String success) {
		return "<div class=\"alert alert-success text-center\" role=\"alert\">\r\n" + 
				"  <strong>Well done!</strong> "+success+"\r\n" + 
				"</div>";
	}

}
