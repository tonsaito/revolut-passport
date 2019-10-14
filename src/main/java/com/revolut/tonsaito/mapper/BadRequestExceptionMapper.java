package com.revolut.tonsaito.mapper;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.tonsaito.model.ResponseModel;

@Provider
public class BadRequestExceptionMapper extends BadRequestException implements ExceptionMapper<BadRequestException>
{
    private static final long serialVersionUID = 1L;
  
    @Override
    public Response toResponse(BadRequestException exception){
        return Response.status(Status.BAD_REQUEST).entity(new ResponseModel.Builder().withStatus(false).withMessage(exception.getMessage()).build()).type("application/json").build();
    }
}