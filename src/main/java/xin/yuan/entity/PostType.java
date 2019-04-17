package xin.yuan.entity;

/**
 * ���ӵ�����
 * @author Administrator
 *
 */
public class PostType {
	
	private int typeID;
	
	private String typeName;
	
	public PostType() {}
	
	public PostType(int typeID, String typeName) {
		this.typeID = typeID;
		this.typeName = typeName;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
