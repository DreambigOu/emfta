/**
 */
package edu.cmu.emfta;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.cmu.emfta.Event#getType <em>Type</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getName <em>Name</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getProbability <em>Probability</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getGate <em>Gate</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getRelatedObject <em>Related Object</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getReferenceCount <em>Reference Count</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getURI <em>URI</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#getUuid <em>Uuid</em>}</li>
 *   <li>{@link edu.cmu.emfta.Event#isSafeGuard <em>Safe Guard</em>}</li>
 * </ul>
 *
 * @see edu.cmu.emfta.EmftaPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.cmu.emfta.EventType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see edu.cmu.emfta.EventType
	 * @see #setType(EventType)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Type()
	 * @model
	 * @generated
	 */
	EventType getType();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see edu.cmu.emfta.EventType
	 * @see #getType()
	 * @generated
	 */
	void setType(EventType value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Name()
	 * @model default=""
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(double)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Probability()
	 * @model
	 * @generated
	 */
	double getProbability();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(double value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Gate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gate</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gate</em>' containment reference.
	 * @see #setGate(Gate)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Gate()
	 * @model containment="true"
	 * @generated
	 */
	Gate getGate();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getGate <em>Gate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gate</em>' containment reference.
	 * @see #getGate()
	 * @generated
	 */
	void setGate(Gate value);

	/**
	 * Returns the value of the '<em><b>Related Object</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Object</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Object</em>' attribute list.
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_RelatedObject()
	 * @model
	 * @generated
	 */
	EList<Object> getRelatedObject();

	/**
	 * Returns the value of the '<em><b>Reference Count</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Count</em>' attribute.
	 * @see #setReferenceCount(int)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_ReferenceCount()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getReferenceCount();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getReferenceCount <em>Reference Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Count</em>' attribute.
	 * @see #getReferenceCount()
	 * @generated
	 */
	void setReferenceCount(int value);

	/**
	 * Returns the value of the '<em><b>URI</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>URI</em>' attribute.
	 * @see #setURI(String)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_URI()
	 * @model default="" id="true"
	 * @generated
	 */
	String getURI();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getURI <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URI</em>' attribute.
	 * @see #getURI()
	 * @generated
	 */
	void setURI(String value);

	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see #setUuid(String)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_Uuid()
	 * @model
	 * @generated
	 */
	String getUuid();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#getUuid <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid</em>' attribute.
	 * @see #getUuid()
	 * @generated
	 */
	void setUuid(String value);

	/**
	 * Returns the value of the '<em><b>Safe Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Safe Guard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Safe Guard</em>' attribute.
	 * @see #setSafeGuard(boolean)
	 * @see edu.cmu.emfta.EmftaPackage#getEvent_SafeGuard()
	 * @model
	 * @generated
	 */
	boolean isSafeGuard();

	/**
	 * Sets the value of the '{@link edu.cmu.emfta.Event#isSafeGuard <em>Safe Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Safe Guard</em>' attribute.
	 * @see #isSafeGuard()
	 * @generated
	 */
	void setSafeGuard(boolean value);

} // Event
