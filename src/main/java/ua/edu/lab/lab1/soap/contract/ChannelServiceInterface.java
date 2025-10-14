package ua.edu.lab.lab1.soap.contract;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.soap.dto.ChannelCreateRequest;
import java.util.List;

@WebService(name = "ChannelService", targetNamespace = "http://lab.edu.ua/lab1/soap")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ChannelServiceInterface {

    @WebMethod(operationName = "getChannelById")
    @WebResult(name = "channel")
    Channel getChannelById(@WebParam(name = "id") Long id);

    @WebMethod(operationName = "addChannel")
    @WebResult(name = "createdChannel")
    Channel addChannel(@WebParam(name = "channelData") ChannelCreateRequest channelData);

    @WebMethod(operationName = "updateChannelDescription")
    @WebResult(name = "updatedChannel")
    Channel updateChannelDescription(@WebParam(name = "id") Long id, @WebParam(name = "newDescription") String newDescription);

    @WebMethod(operationName = "getAllChannels")
    @WebResult(name = "channels")
    List<Channel> getAllChannels();
}