package org.spgroup.controller;

import java.util.List;

import org.spgroup.request.SendRequest;
import org.spgroup.request.UpdateRequest;
import org.spgroup.response.FriendsResponse;
import org.spgroup.response.RecipientsResponse;
import org.spgroup.response.SuccessResponse;
import org.spgroup.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

	@Autowired
	FriendService friendService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloworld() {
		return "Please use following END Points : \n "
				+ "1. Add Friends [ /add ] \n"
				+ "2. Common Frineds [ /common ] \n"
				+ "3. Get Friends [ /friends ] \n"
				+ "4. Subscribe [ /subscribe ] \n"
				+ "5. Block [ /block ] \n"
				+ "6. Recipients [ /recipients ]\n"
				+ "\n"
				+ "Use Swagger UI for testing rest calls: [ /swagger-ui.html ]";
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public SuccessResponse addFriend(@RequestBody List<String> friends) {

		boolean success = friendService.addFriends(friends);
		return new SuccessResponse(success);
	}

	@ResponseBody
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public FriendsResponse getFriends(@RequestParam String email) {
		List<String> friends = friendService.getFriends(email);
		return new FriendsResponse(friends);
	}

	@ResponseBody
	@RequestMapping(value = "/common", method = RequestMethod.POST)
	public FriendsResponse commonFriends(@RequestBody List<String> friends) {
		List<String> common = friendService.getCommonFriends(friends);
		return new FriendsResponse(common);
	}

	@ResponseBody
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public SuccessResponse subscribe(@RequestBody UpdateRequest request) {
		boolean success = friendService.subscribe(request.getRequestor(),
				request.getTarget());
		return new SuccessResponse(success);
	}

	@ResponseBody
	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public SuccessResponse block(@RequestBody UpdateRequest request) {
		boolean success = friendService.block(request.getRequestor(),
				request.getTarget());
		return new SuccessResponse(success);
	}

	@ResponseBody
	@RequestMapping(value = "/recipients", method = RequestMethod.POST)
	public RecipientsResponse canReceive(@RequestBody SendRequest message) {
		List<String> recipients = friendService.getRecipients(message.getSender(), message.getText());
		if (recipients == null)
			return new RecipientsResponse(false, null);
		return new RecipientsResponse(true, recipients);
	}
}
