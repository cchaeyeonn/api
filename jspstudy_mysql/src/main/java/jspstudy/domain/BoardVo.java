//BoardVo.java

package jspstudy.domain;

public class BoardVo {
	
	private int tidx;	//데이터를 담는 변수니까 외부에서 접근하지 못하게 private으로 막는다
	private int origintidx;
	private int t_depth;
	private int t_level_;
	private String t_head;
	private String t_subject;
	private String t_content;
	private String t_writer;
	private String t_writeday;
	private String t_delyn;
	private String t_ip;
	private int m_midx;
	private String t_filename;
	
	
	public String getT_head() {
		return t_head;
	}
	public void setT_head(String t_head) {
		this.t_head = t_head;
	}
	
	public int getTidx() {
		return tidx;
	}
	public void setTidx(int tidx) {
		this.tidx = tidx;
	}
	public int getOrigintidx() {
		return origintidx;
	}
	public void setOrigintidx(int origintidx) {
		this.origintidx = origintidx;
	}
	public int getT_depth() {
		return t_depth;
	}
	public void setT_depth(int t_depth) {
		this.t_depth = t_depth;
	}
	public int getT_level_() {
		return t_level_;
	}
	public void setT_level_(int t_level_) {
		this.t_level_ = t_level_;
	}
	public String getT_subject() {
		return t_subject;
	}
	public void setT_subject(String t_subject) {
		this.t_subject = t_subject;
	}
	public String getT_content() {
		return t_content;
	}
	public void setT_content(String t_content) {
		this.t_content = t_content;
	}
	public String getT_writer() {
		return t_writer;
	}
	public void setT_writer(String t_writer) {
		this.t_writer = t_writer;
	}
	public String getT_writeday() {
		return t_writeday;
	}
	public void setT_writeday(String t_writeday) {
		this.t_writeday = t_writeday;
	}
	public String getT_delyn() {
		return t_delyn;
	}
	public void setT_delyn(String t_delyn) {
		this.t_delyn = t_delyn;
	}
	public String getT_ip() {
		return t_ip;
	}
	public void setT_ip(String t_ip) {
		this.t_ip = t_ip;
	}
	public int getM_midx() {
		return m_midx;
	}
	public void setM_midx(int m_midx) {
		this.m_midx = m_midx;
	}
	public String getT_filename() {
		return t_filename;
	}
	public void setT_filename(String t_filename) {
		this.t_filename = t_filename;
	}
	
	
}
