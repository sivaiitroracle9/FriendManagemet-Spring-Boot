package org.spgroup.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipientsResponse {

	private boolean success;
	private List<String> recipients;
}
