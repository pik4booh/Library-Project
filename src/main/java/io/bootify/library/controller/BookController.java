package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.CopyBook;
import io.bootify.library.model.BookDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.CustomBookRepository;
import io.bootify.library.service.BookService;
import io.bootify.library.service.CategoryService;
import io.bootify.library.util.ReferencedWarning;
import io.bootify.library.util.WebUtils;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final CustomBookRepository customBookRepository;

    public BookController(final BookService bookService, final CategoryService categoryService, final BookRepository bookRepository, final CustomBookRepository customBookRepository) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.customBookRepository = customBookRepository;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        List<Object[]> mostBorrowedBooks = bookService.getNMostBorrowedBooks(3);
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
        return "book/list";
    }

    @GetMapping("/search")
    public String searchBooks(
        @RequestParam(name = "title", required = false) String title,
        @RequestParam(name = "author", required = false) String author,
        @RequestParam(name = "releaseDate1", required = false) String releaseDate1,
        @RequestParam(name = "releaseDate2", required = false) String releaseDate2,
        @RequestParam(name = "categories", required = false) List<String> categories,
        Model model,
        RedirectAttributes redirectAttributes) {

        System.out.println("hehe");
        System.out.println("title"+title);
        System.out.println("author"+author);
        System.out.println("releaseDate1"+releaseDate1);
        System.out.println("releaseDate2"+releaseDate2);

        if(title == null && author == null && releaseDate1 == null && releaseDate2 == null && categories == null)
        {
            redirectAttributes.addFlashAttribute("error", "Please enter at least one search criteria");
            System.out.println("Please enter at least one search criteria");
            return "redirect:/books";
        }

        LocalDate date1 = null;
        LocalDate date2 = null;

        // Check if releaseDate1 is not empty, then parse it to LocalDate
        if (releaseDate1 != null) {
            date1 = LocalDate.parse(releaseDate1);
        }

        // Check if releaseDate2 is not empty, then parse it to LocalDate
        if (releaseDate2 != null) {
            date2 = LocalDate.parse(releaseDate2);
        }

        System.out.println("date1"+date1);
        System.out.println("date2"+date2);

        List<Book> books = customBookRepository .findBooksByCriteria(title,author,date1,date2,categories);
        for (Book b : books) {
            System.out.println("title"+b.getTitle());
        }
        model.addAttribute("books", books);

        return "book/bookSearch";
    }

    @GetMapping("/listCopyBooks/{idBook}")
    public String listCopyBooks(@PathVariable(name = "idBook") final Integer idBook, final Model model) {
        System.out.println("hehe");
        System.out.println("idBook: " + idBook);

        // Optional<Book> optionalBook = bookRepository.findById(idBook);

        // if (!optionalBook.isPresent()) {
        //     System.out.println("Book not found with id: " + idBook);
        //     model.addAttribute("error", "Book not found");
        //     return "errorPage"; // Replace with your actual error page
        // }

        // Book book = optionalBook.get();
        // System.out.println("book: " + book);

        // // Initialize the copyBooks collection within the Hibernate session
        // Hibernate.initialize(book.getCopyBooks());

        // Set<CopyBook> copyBooks = book.getCopyBooks();
        // for (CopyBook copyBook : copyBooks) {
        //     System.out.println("Isbn: " + copyBook.getIsbn());
        // }

        List<Object[]> copyBooks = bookService.getAvailableCopyBooks(idBook);
        model.addAttribute("copyBooks", copyBooks);
        return "book/listCopyBooks";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("book") final BookDTO bookDTO) {
        return "book/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("book") @Valid final BookDTO bookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/add";
        }
        bookService.create(bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.create.success"));
        return "redirect:/books";
    }

    @GetMapping("/edit/{idBook}")
    public String edit(@PathVariable(name = "idBook") final Integer idBook, final Model model) {
        model.addAttribute("book", bookService.get(idBook));
        return "book/edit";
    }

    @PostMapping("/edit/{idBook}")
    public String edit(@PathVariable(name = "idBook") final Integer idBook,
            @ModelAttribute("book") @Valid final BookDTO bookDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(idBook, bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.update.success"));
        return "redirect:/books";
    }

    @PostMapping("/delete/{idBook}")
    public String delete(@PathVariable(name = "idBook") final Integer idBook,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = bookService.getReferencedWarning(idBook);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            bookService.delete(idBook);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("book.delete.success"));
        }
        return "redirect:/books";
    }

}
