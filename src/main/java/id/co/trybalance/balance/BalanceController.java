package id.co.trybalance.balance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalanceController {

	@PostMapping("/balance")
	  public String reqBal(@RequestBody RequestBalance newReq) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");  
		LocalDateTime now = LocalDateTime.now();  
		RestTemplate restTemplate = new RestTemplate();
		JSONObject mpi = new JSONObject();
		mpi.put("ZLEAN", newReq.getNorek());
		JSONObject reqJson = new JSONObject();
		reqJson.put("CID", newReq.getCID());
		reqJson.put("DT", "20220627");
		reqJson.put("ST", dtf.format(now));
		reqJson.put("MC", "90023");
		reqJson.put("PC", "001001");
		reqJson.put("PCC", "5");
		reqJson.put("FC", "AB");
		reqJson.put("MT", "9200");
		reqJson.put("CC", "0001");
		reqJson.put("MPI", mpi);
		String url = newReq.getUri();
		String requestJsonString = "";
		requestJsonString = reqJson.toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJsonString,headers);
		String answer = restTemplate.postForObject(url, entity, String.class);
		
		JSONObject json = new JSONObject(answer);
		String RC = json.getString("RC");
		if(RC.equalsIgnoreCase("0000")) {
			return "Balance is " +json.getJSONObject("MPO").getJSONObject("0").getString("ZLBAL");
		}
		else {
			return "Balance is error";
		}
		
		
	  }
	
}
