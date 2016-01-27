package com.sooki.components;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MyNode {
	int id;
	String name;
	int type;
	int coord_x;
	int coord_y;
public MyNode()
{
	

}

public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getX() {
	return coord_x;
}

public void setX(int x) {
	this.coord_x = x;
}

public int getY() {
	return coord_y;
}

public void setY(int y) {
	this.coord_y = y;
}

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}


@Override
public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(name).
            append(id).
            append(type).
            toHashCode();
 }

@Override
public boolean equals(Object obj) {
   if (!(obj instanceof MyNode))
        return false;
    if (obj == this)
        return true;

    MyNode rhs = (MyNode) obj;
    return new EqualsBuilder().
        // if deriving: appendSuper(super.equals(obj)).
        append(name, rhs.name).
        append(id, rhs.id).
        isEquals();
}

public String toString()
{
	return this.name;
	
}


}
