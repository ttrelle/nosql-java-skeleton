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
	
    @RequestMapping("/findAll")
    @ResponseBody
    String find(@RequestParam String db, @RequestParam String c) throws UnknownHostException {
    	LOG.info( "DB: " + db + " Collection: " + c );
    	
    	MongoClient client = new MongoClient();
    	DBCollection coll = client.getDB(db).getCollection(c);
    	DBCursor crsr = coll.find();
    	
        return toJsonArray(crsr);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleClient.class, args);
    }
    
    private static String toJsonArray(DBCursor crsr) {
    	final StringBuilder sb = new StringBuilder("[\n");
    	boolean first = true;
    	
    	while ( crsr.hasNext() ) {
    		if (!first) {
    			sb.append(" ,\n");
    		}
    		first = false;
    		sb.append( crsr.next().toString() );
    	}
    	sb.append("\n]");
    	
        return sb.toString();
    }
}
