package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BookRepository bookRepository;

    public CustomBookRepositoryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findBooksByCriteria(String title, String author, LocalDate releaseDate1, LocalDate releaseDate2, List<String> categories) {
        StringBuilder query = new StringBuilder("SELECT b.* " +
        "FROM public.book AS b " +
        "LEFT JOIN public.book_category AS bc ON b.id_book = bc.book_id " +
        "LEFT JOIN public.category AS c ON c.id_category = bc.category_id " +
        "WHERE (COALESCE(:title, '') = '' OR b.title LIKE :title) " +
        "AND (COALESCE(:author, '') = '' OR b.author LIKE :author) " +
        "AND (COALESCE(:releaseDate1, NULL) IS NULL OR b.release_date >= :releaseDate1) " +
        "AND (COALESCE(:releaseDate2, NULL) IS NULL OR b.release_date <= :releaseDate2) ");

        if (categories != null && !categories.isEmpty()) {
            query.append("AND c.name IN (:categories) ");
        }else {
            query.append("AND COALESCE(:categories, '') = '' ");
        }

        if(title != null){
            query.append("UNION "+
            "SELECT b.* "+
        
            "FROM public.book AS b "+
            "WHERE (COALESCE(:title, '') = '' OR b.title LIKE :title) "
            );
        }

        if(author != null){
            query.append("UNION "+
            "SELECT b.* "+
            "FROM public.book AS b "+
            "WHERE (COALESCE(:author, '') = '' OR b.author LIKE :author) "
            );
        }
        
        if (categories != null && !categories.isEmpty()) {
            query.append("UNION "+
            "SELECT b.* "+
            "FROM public.book AS b "+
            "LEFT JOIN public.book_category AS bc ON b.id_book = bc.book_id " +
            "LEFT JOIN public.category AS c ON c.id_category = bc.category_id " +
            "WHERE c.name IN (:categories) ");
        }

        if(releaseDate1 != null || releaseDate2 != null){
            query.append(
            "UNION "+
            "SELECT b.* "+
            "FROM public.book AS b "+
            "WHERE (COALESCE(:releaseDate1, NULL) IS NULL OR b.release_date >= :releaseDate1) "+
            "AND (COALESCE(:releaseDate2, NULL) IS NULL OR b.release_date <= :releaseDate2) "
            );
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", title != null ? "%" + title + "%" : null);
        params.addValue("author", author != null ? "%" + author + "%" : null);
        params.addValue("releaseDate1", releaseDate1);
        params.addValue("releaseDate2", releaseDate2);
        params.addValue("categories", categories != null && !categories.isEmpty() ? categories : null);

        System.out.println("query");
        String debugQuery = generateDebugQuery(query.toString(), params);
        System.out.println(debugQuery);

        List<Book> listBook = namedParameterJdbcTemplate.query(query.toString(), params, new BookRowMapper());
        for (Book book : listBook) {
            System.out.println(book.getTitle());   
        }
        return namedParameterJdbcTemplate.query(query.toString(), params, new BookRowMapper());
    }

    private String generateDebugQuery(String query, MapSqlParameterSource params) {
        String debugQuery = query;
        for (String paramName : params.getParameterNames()) {
            Object value = params.getValue(paramName);
            String valueStr = value instanceof String ? "'" + value + "'" : String.valueOf(value);
            if (value instanceof List) {
                valueStr = ((List<?>) value).stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("', '", "('", "')"));
            }
            debugQuery = debugQuery.replace(":" + paramName, valueStr);
        }
        return debugQuery;
    }

    private static final class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setIdBook(rs.getInt("id_book")); // Adjust column name accordingly
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setSummary(rs.getString("summary"));
            book.setCollection(rs.getString("collection"));
            book.setCoteNumber(rs.getString("cote_number"));
            
            Timestamp timestamp = rs.getTimestamp("release_date");
            if (timestamp != null) {
                book.setReleaseDate(timestamp.toLocalDateTime());
            }
            // Set other fields as needed
            return book;
        }
    }

}
