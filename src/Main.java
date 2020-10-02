import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import processing.core.PApplet;

public class Main extends PApplet{
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedWriter write;
	private BufferedReader read;
	private ArrayList <Usuario> users;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void settings () {
		size (500, 500);
	}
	
	public void setup () {
		initServer();
		users = new ArrayList <Usuario>();
		
		users.add(new Usuario ("mattvidarte:matheus123"));
		users.add(new Usuario ("lauraleon:leon123"));
		users.add(new Usuario ("davidVS:vsDavid"));
			
		
	}
	
	public void draw () {
		background (255);
		
	}
	
	public void initServer () {
		
		new Thread (
				()->{
					try {
						serverSocket = new ServerSocket(5000);
						System.out.println("Esperando conexión");
						socket = serverSocket.accept();
						System.out.println("conectado");
						
						InputStream is = socket.getInputStream();
						InputStreamReader isr = new InputStreamReader (is);
						read = new BufferedReader (isr);
					
						
						OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter (os);
						write = new BufferedWriter (osw);
						
						while (true) {
							String line = read.readLine();
							/*String [] part = line.split(":");
							String userr = part[0];
							String passdwordd = part [1];*/
							
							for (int i = 0; i<users.size(); i++) {
									
								if (line.equals(users)) {
									fill (0);
									textSize(50);
									text("Bienvenido", 250,250);
								}
							}
							
							System.out.println("mensaje recibido:"+line);
						}
						
						
								
					} catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				).start();
	}
	
	
}
