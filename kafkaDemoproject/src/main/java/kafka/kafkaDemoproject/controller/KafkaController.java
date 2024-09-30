package kafka.kafkaDemoproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kafka.kafkaDemoproject.producer.MessageProducer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KafkaController {

	@Autowired
	private MessageProducer messageProducer;

	private List<String> messages = new ArrayList<>();

	@PostMapping("/send")
	public String sendMessage(@RequestBody String message) {
		messageProducer.sendToTopics(message);
		messages.add(message);
		return "Message sent to topics!";
	}

	@GetMapping("/messages")
	public List<String> getMessages() {
		return messages;
	}

	@GetMapping("/messages/{index}")
	public String getMessage(@PathVariable int index) {
		if (index < 0 || index >= messages.size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		return messages.get(index);
	}

	@PutMapping("/messages/{index}")
	public String updateMessage(@PathVariable int index, @RequestBody String newMessage) {
		if (index < 0 || index >= messages.size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		String oldMessage = messages.set(index, newMessage); // Update message
		return "Message updated from '" + oldMessage + "' to '" + newMessage + "'";
	}

	@DeleteMapping("/messages/{index}")
	public String deleteMessage(@PathVariable int index) {
		if (index < 0 || index >= messages.size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		String removedMessage = messages.remove(index); // Delete message
		return "Message '" + removedMessage + "' deleted.";
	}
}
