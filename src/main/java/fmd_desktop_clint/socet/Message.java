/**
 * @author mohamed265
 * Created On : Dec 11, 2015 3:32:21 PM
 */
package fmd_desktop_clint.socet;

import java.io.Serializable;

/**
 * @author mohamed265
 *
 */

public abstract class Message  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String content;
	private Boolean type;

	public Message() {
	}

	public abstract Object getSender();

	public abstract Object getSenderId();

	public abstract Object getReceiever();

	public abstract Object getReceieverId();

	public Long getId() {
		return id;
	}

	public Boolean getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
