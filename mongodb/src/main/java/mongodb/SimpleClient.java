package mongodb;

import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Controller
@EnableAutoConfiguration
public class SimpleClient {

	private static final Log LOG = LogFactory.getLog(SimpleClient.class);
	
    @RequestMapping("/findAll/{db}/{collection}")
    @ResponseBody
    String find(@PathVariable String db, @PathVariable String collection) throws UnknownHostException {
    	LOG.info( "DB: " + db + " Collection: " + collection );
    	
    	MongoClient client = new MongoClient();
    	DBCollection c = client.getDB(db).getCollection(collection);
    	DBCursor crsr = c.find();
    	StringBuilder sb = new StringBuilder("[ ");
    	
    	boolean first = true;
    	while ( crsr.hasNext() ) {
    		if (!first) {
    			sb.append(", ");
    		}
    		first = false;
    		sb.append( crsr.next().toString() );
    	}
    	sb.append(" ]");
    	
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleClient.class, args);
    }
}
