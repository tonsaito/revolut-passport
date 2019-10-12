package com.revolut.tonsaito.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.tonsaito.model.ResponseModel;

@Provider
public class UncaughtExceptionMapper  extends Throwable implements ExceptionMapper<Throwable>
{
    private static final long serialVersionUID = 1L;
  
    @Override
    public Response toResponse(Throwable exception){
        return Response.status(Status.BAD_REQUEST).entity(new ResponseModel.Builder().withStatus(false).withMessage("Invalid Request. Please, try again.").build()).type("application/json").build();
    }
}