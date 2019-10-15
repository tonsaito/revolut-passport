package com.revolut.tonsaito.mapper;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.tonsaito.model.ResponseModel;

@Provider
public class InternalServerErrorExceptionMapper extends InternalServerErrorException implements ExceptionMapper<InternalServerErrorException>
{
    private static final long serialVersionUID = 1L;
  
    @Override
    public Response toResponse(InternalServerErrorException exception){
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseModel.Builder().withStatus(false).withMessage(exception.getMessage()).build()).type("application/json").build();
    }
}