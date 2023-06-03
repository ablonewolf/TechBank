package com.arka99.account.cmd.api.controllers;

import com.arka99.account.cmd.api.command.OpenAccountCommand;
import com.arka99.account.cmd.api.dto.OpenAccountResponse;
import com.arka99.account.common.dto.BaseResponse;
import com.arka99.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand openAccountCommand) {
        var id = UUID.randomUUID().toString();
        openAccountCommand.setId(id);
        try {
            commandDispatcher.send(openAccountCommand);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account creation request completed successfully"), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.", e.getMessage()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = MessageFormat.format("Error while processing a bank account for id {0}.", id);
            logger.log(Level.SEVERE, errorMessage);
            return new ResponseEntity<>(new OpenAccountResponse(errorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
