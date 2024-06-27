package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookTheme;
import io.bootify.library.domain.Category;
import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Theme;
import io.bootify.library.model.BookCategoryDTO;
import io.bootify.library.model.BookDTO;
import io.bootify.library.model.BookThemeDTO;
import io.bootify.library.model.LibrarianDTO;
import io.bootify.library.model.NewBookDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.CustomBookRepository;
import io.bootify.library.service.BookCategoryService;
import io.bootify.library.service.BookService;
import io.bootify.library.service.BookThemeService;
import io.bootify.library.service.CategoryService;
import io.bootify.library.service.ThemeService;
import io.bootify.library.util.ReferencedWarning;
import io.bootify.library.util.WebUtils;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

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
    private final ThemeService themeService;
    private final BookCategoryService bookCategoryService;
    private final BookThemeService bookThemeService;
    
    public BookController(final BookService bookService, final CategoryService categoryService,
    final BookRepository bookRepository, final CustomBookRepository customBookRepository,
    final ThemeService themeService, final BookCategoryService bookCategoryService,
    final BookThemeService bookThemeService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.customBookRepository = customBookRepository;
        this.themeService = themeService;
        this.bookCategoryService = bookCategoryService;
        this.bookThemeService = bookThemeService;
    }

    @GetMapping
    public String list(final Model model) {
        
        try {
            // model.addAttribute("books", bookService.findAll());
            // model.addAttribute("categories", categoryService.findAll());

            List<Object[]> mostBorrowedBooks = bookService.getNMostBorrowedBooks(3);
            model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
            return "book/list";
        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error", e.getMessage());
            return "book/list";
        }
        
    }

    @GetMapping("/listSearch")
    public String listSearch(final Model model) {
        
        try {
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("categories", categoryService.findAll());

            List<Object[]> mostBorrowedBooks = bookService.getNMostBorrowedBooks(3);
            model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
            return "book/listSearch";
        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error", e.getMessage());
            return "book/listSearch";
        }
        
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

        if(title == null && author == null && releaseDate1 == null && releaseDate2 == null && categories == null)
        {
            redirectAttributes.addFlashAttribute("info", "Ajouter au moins un critère");
            return "redirect:/books/listSearch";
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

        try {
            List<Book> books = customBookRepository .findBooksByCriteria(title,author,date1,date2,categories);
            model.addAttribute("books", books);
            return "book/bookSearch";

        } catch (Exception e) {
            // TODO: handle exception
            redirectAttributes.addFlashAttribute("info", e.getMessage());
            return "book/bookSearch";
        }
        
    }

    @GetMapping("/listCopyBooks/{idBook}")
    public String listCopyBooks(@PathVariable(name = "idBook") final Integer idBook, final Model model) {
        model.addAttribute("bookValue", idBook);

        Book book = bookRepository.findById(idBook).get();
        model.addAttribute("book", book);

        try {
            List<Object[]> copyBooks = bookService.getAvailableCopyBooks(idBook);
            model.addAttribute("copyBooks", copyBooks);
            return "book/listCopyBooks";
        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error", e.getMessage());
            return "book/listCopyBooks";
        }
        
    }

    //add new book copyboook
    @GetMapping("/add/{idBook}")
    public String add(@PathVariable(name = "idBook") final Integer idBook, @ModelAttribute("book") final BookDTO bookDTO, final Model model) {
        model.addAttribute("bookValue", idBook);
        return "book/add";
    }

    // go to the add book page
    @GetMapping("/add")
    public String add(@ModelAttribute("book") final BookDTO bookDTO, final Model model) {
        try {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("newBookDTO", new NewBookDTO());
            return "book/add";
        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error", e.getMessage());
            return "book/add";
        }
    }

    //insert new book
    @PostMapping("/add")
    public String add( @ModelAttribute @Valid final NewBookDTO newBookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        System.out.println("Title: " + newBookDTO.getTitle());
        System.out.println("Summary: " + newBookDTO.getSummary());
        System.out.println("Collection: " + newBookDTO.getCollection());
        System.out.println("Cote Number: " + newBookDTO.getCoteNumber());
        System.out.println("Release Date: " + newBookDTO.getReleaseDate());
        System.out.println("Author: " + newBookDTO.getAuthor());
        System.out.println("Categories:");
        for (Category category : newBookDTO.getCategories()) {
            System.out.println("- " + category.getName());
        }
        System.out.println("Themes:");
        for (Theme theme : newBookDTO.getThemes()) {
            System.out.println("- " + theme.getName());
        }
                
        // if (bindingResult.hasErrors()) {
        //     return "redirect:/books/add";
        // }

        BookDTO bookDTO = new BookDTO();
            bookDTO.setTitle(newBookDTO.getTitle());
            bookDTO.setSummary(newBookDTO.getSummary());
            bookDTO.setCollection(newBookDTO.getCollection());
            bookDTO.setCoteNumber(newBookDTO.getCoteNumber());
            bookDTO.setReleaseDate(newBookDTO.getReleaseDate());
            bookDTO.setAuthor(newBookDTO.getAuthor());

        try {
            Integer idBook = bookService.create(bookDTO);
            System.out.println("new idbook"+idBook);

            if(newBookDTO.getCategories() != null) {
                for (Category category : newBookDTO.getCategories()) {
                    BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();
                        bookCategoryDTO.setBook(idBook);
                        bookCategoryDTO.setCategory(category.getIdCategory());
                    bookCategoryService.create(bookCategoryDTO);
                }
            }

            if(newBookDTO.getThemes() != null) {
                for (Theme theme : newBookDTO.getThemes()) {
                    BookThemeDTO bookThemeDTO = new BookThemeDTO();
                        bookThemeDTO.setBook(idBook);
                        bookThemeDTO.setTheme(theme.getIdTheme());
                    bookThemeService.create(bookThemeDTO);
                }
            }

            redirectAttributes.addFlashAttribute("success", "Livre créé avec succès");
            return "redirect:/books/add";

        } catch (Exception e) {
            // TODO: handle exception
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books/add";
        }
        
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
        return "redirect:/books/listSearch";
    }

    @PostMapping("/delete/{idBook}")
    public String delete(@PathVariable(name = "idBook") final Integer idBook,
        final RedirectAttributes redirectAttributes) {
            final ReferencedWarning referencedWarning = bookService.getReferencedWarning(idBook);
            System.out.println("hehe"+referencedWarning);
            try {
                if (referencedWarning != null) {
                    redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                            WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
                } else {
                    bookService.delete(idBook);
                    System.out.println("success");
                    redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("book.delete.success"));
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
        
        return "redirect:/books/listSearch";
    }

}
