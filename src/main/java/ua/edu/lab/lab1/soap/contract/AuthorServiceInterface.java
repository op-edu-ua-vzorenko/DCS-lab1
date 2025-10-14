package ua.edu.lab.lab1.soap.contract;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import ua.edu.lab.lab1.model.Author;
import ua.edu.lab.lab1.soap.dto.AuthorCreateRequest;
import java.util.List;

// JAX-WS анотації для створення SOAP-сервісу
@WebService(name = "AuthorService", targetNamespace = "http://lab.edu.ua/lab1/soap")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AuthorServiceInterface {

    @WebMethod(operationName = "getAuthorById")
    @WebResult(name = "author") // Як буде називатись тег у відповіді
    Author getAuthorById(@WebParam(name = "id") Long id);

    @WebMethod(operationName = "addAuthor")
    @WebResult(name = "createdAuthor")
    Author addAuthor(@WebParam(name = "authorData") AuthorCreateRequest authorData);

    @WebMethod(operationName = "deleteAuthor")
    @WebResult(name = "success")
    boolean deleteAuthor(@WebParam(name = "id") Long id);

    @WebMethod(operationName = "getAllAuthors")
    @WebResult(name = "authors")
    List<Author> getAllAuthors();
}