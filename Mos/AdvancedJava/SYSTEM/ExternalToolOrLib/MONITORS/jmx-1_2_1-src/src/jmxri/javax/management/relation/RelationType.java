/*
 * @(#)file      RelationType.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.14
 * @(#)lastedit      03/07/07
 *
 * Copyright 2000 Sun Microsystems, Inc. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 * 
 * Copyright 2000 Sun Microsystems, Inc. Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation. 
 */

package javax.management.relation;

import java.util.ArrayList; // for Javadoc
import java.util.List;

import java.io.Serializable;

/**
 * The RelationType interface has to be implemented by any class expected to
 * represent a relation type.
 *
 * @since-jdkbundle 1.5
 */
public interface RelationType extends Serializable {

    //
    // Accessors
    //

    /**
     * Returns the relation type name.
     *
     * @return the relation type name.
     */
    public String getRelationTypeName();

    /**
     * Returns the list of role definitions (ArrayList of RoleInfo objects).
     *
     * @return an {@link ArrayList} of {@link RoleInfo}.
     */
    public List getRoleInfos();

    /**
     * Returns the role info (RoleInfo object) for the given role info name
     * (null if not found).
     *
     * @param theRoleInfoName  role info name
     *
     * @return RoleInfo object providing role definition
     * does not exist
     *
     * @exception IllegalArgumentException  if null parameter
     * @exception RoleInfoNotFoundException  if no role info with that name in
     * relation type.
     */
    public RoleInfo getRoleInfo(String theRoleInfoName)
	throws IllegalArgumentException,
	       RoleInfoNotFoundException;
}
