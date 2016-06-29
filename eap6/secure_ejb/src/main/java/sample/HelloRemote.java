package sample;

import javax.ejb.Remote;

@Remote
public interface HelloRemote {

	public String hello();
}
