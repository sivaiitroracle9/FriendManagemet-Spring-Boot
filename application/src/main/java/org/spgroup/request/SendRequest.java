package org.spgroup.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendRequest {
	
	private String sender;
	private String text;
}
