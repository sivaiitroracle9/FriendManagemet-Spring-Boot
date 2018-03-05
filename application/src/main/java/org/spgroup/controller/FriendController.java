package org.spgroup.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spgroup.common.ApiException;
import org.spgroup.common.EntityConstants;
import org.spgroup.common.InputValidator;
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
	private static Logger logger = LoggerFactory
			.getLogger(FriendController.class);
	@Autowired
	FriendService friendService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloworld() {
		return EntityConstants.helloworld;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public SuccessResponse add(@RequestBody List<String> friends)
			throws ApiException {
		InputValidator.friends(friends);
		try {
			boolean success = friendService.addFriends(friends);

			return new SuccessResponse(success);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw e;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public FriendsResponse friends(
			@RequestParam(name = "email", required = true) String email)
			throws ApiException {
		InputValidator.email(email);

		try {
			List<String> friends = friendService.getFriends(email);
			return new FriendsResponse(friends);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/common", method = RequestMethod.POST)
	public FriendsResponse common(@RequestBody List<String> friends)
			throws ApiException {
		InputValidator.friends(friends);

		try {
			List<String> common = friendService.getCommonFriends(friends);
			return new FriendsResponse(common);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public SuccessResponse subscribe(@RequestBody UpdateRequest request)
			throws ApiException {
		InputValidator.email(request.getRequestor(), request.getTarget());

		try {
			boolean success = friendService.subscribe(request.getRequestor(),
					request.getTarget());
			return new SuccessResponse(success);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public SuccessResponse block(@RequestBody UpdateRequest request)
			throws ApiException {
		InputValidator.email(request.getRequestor(), request.getTarget());

		try {
			boolean success = friendService.block(request.getRequestor(),
					request.getTarget());
			return new SuccessResponse(success);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/recipients", method = RequestMethod.POST)
	public RecipientsResponse recipients(@RequestBody SendRequest message)
			throws ApiException {
		InputValidator.email(message.getSender());

		try {
			List<String> recipients = friendService.getRecipients(
					message.getSender(), message.getText());
			if (recipients == null)
				return new RecipientsResponse(false, null);
			return new RecipientsResponse(true, recipients);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}
}
