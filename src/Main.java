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

import com.google.gson.Gson;

import model.Usuario;
import processing.core.PApplet;

public class Main extends PApplet{
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedWriter write;
	private BufferedReader read;
	//private Usuario [] users;
	private ArrayList <Usuario> users;
	private int pantalla;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void settings () {
		size (500, 500);
	}
	
	public void setup () {
		initServer();
		pantalla = 0;
		
		
			
		
	}
	
	public void draw () {
		background (255);
		switch (pantalla) {
			case 0:
				fill (0);
				textSize (20);
				textAlign(CENTER);
				text("Ingrese usuario y contraseña", 250, 250);
				break;
			case 1:
				fill (0);
				textSize (20);
				textAlign(CENTER);
				text("Bienvenido", 250, 250);
		}
		
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
							
							Gson gson = new Gson();
							Usuario usuario = gson.fromJson(line, Usuario.class);
							
							
							
							users = new ArrayList <Usuario>();
							users.add(new Usuario ("mattvidarte","matheus123"));
							users.add(new Usuario ("lauraleon","leon123"));
							users.add(new Usuario ("davidVS","vsDavid"));
							
							for (int i = 0; i< users.size(); i++) {
								if (usuario.getUser().equals(users.get(i).getUser())) {
									if (usuario.getPassword().equals(users.get(i).getPassword())) {
									pantalla = 1;
									sendMessage ("Exito");
									
								} else {
									sendMessage ("Noes"); 
								}
								
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
	
	public void sendMessage (String msg) {
		new Thread (
                () -> {
                    try {
                        write.write(msg+"\n");
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
	}
	
	
}
