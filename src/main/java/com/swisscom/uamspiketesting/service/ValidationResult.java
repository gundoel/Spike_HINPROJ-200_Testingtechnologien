package com.swisscom.uamspiketesting.service;

import com.swisscom.uamspiketesting.constant.ValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationResult {
   private String objectId;
   private ValidationStatus status;
   private List<ValidationMessage> messages;

   public ValidationResult(String objectId) {
      this.objectId = objectId;
      this.status = ValidationStatus.OK;
      this.messages = new ArrayList<>();
   }

   public void addError(String fieldId, String message) {
      if (status != ValidationStatus.ERROR) {
         status = ValidationStatus.ERROR;
      }
      messages.add(new ValidationMessage(fieldId, ValidationStatus.ERROR, message));
   }

   public void addWarning(String fieldId, String message) {
      if (status != ValidationStatus.ERROR) {
         status = ValidationStatus.WARNING;
      }
      messages.add(new ValidationMessage(fieldId, ValidationStatus.WARNING, message));
   }

   @AllArgsConstructor
   @Data
   public class ValidationMessage {
      private String fieldId;
      private ValidationStatus status;
      private String message;
   }
}
