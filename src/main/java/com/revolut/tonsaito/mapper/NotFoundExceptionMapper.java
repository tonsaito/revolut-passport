package com.revolut.tonsaito.mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.tonsaito.model.ResponseModel;

@Provider
public class NotFoundExceptionMapper extends NotFoundException implements ExceptionMapper<NotFoundException>
{
    private static final long serialVersionUID = 1L;
  
    @Override
    public Response toResponse(NotFoundException exception){
        return Response.status(Status.NOT_FOUND).entity(new ResponseModel.Builder().withStatus(false).withMessage(exception.getMessage()).build()).type("application/json").build();
    }
}