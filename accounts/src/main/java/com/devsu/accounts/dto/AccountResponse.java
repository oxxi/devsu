package com.devsu.accounts.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse extends AccountRequest
{
   private Long id;

   private boolean status;
}
