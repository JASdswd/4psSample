import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class WebService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//BufferedReader br = 
		
			try{
				URL url = new URL("http://beta.mcct-dswd.com/McctService.svc/Mcct/family/province/batangas");
				URLConnection con = url.openConnection();
				InputStream is = con.getInputStream();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
					
				String line = null;
				
				while((line = br.readLine()) != null){
					System.out.println(line);
				}
				
			}
			catch(Exception ex){
				System.out.println(ex);
			}

	}

}
