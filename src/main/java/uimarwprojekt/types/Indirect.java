

/* First created by JCasGen Thu Oct 16 11:34:01 CEST 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Indirect speech, thought or writing
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class Indirect extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Indirect.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Indirect() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Indirect(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Indirect(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Indirect(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: indtype

  /** getter for indtype - gets Type of the indirect STW
   * @generated
   * @return value of the feature 
   */
  public String getIndtype() {
    if (Indirect_Type.featOkTst && ((Indirect_Type)jcasType).casFeat_indtype == null)
      jcasType.jcas.throwFeatMissing("indtype", "uimarwprojekt.types.Indirect");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Indirect_Type)jcasType).casFeatCode_indtype);}
    
  /** setter for indtype - sets Type of the indirect STW 
   * @generated
   * @param v value to set into the feature 
   */
  public void setIndtype(String v) {
    if (Indirect_Type.featOkTst && ((Indirect_Type)jcasType).casFeat_indtype == null)
      jcasType.jcas.throwFeatMissing("indtype", "uimarwprojekt.types.Indirect");
    jcasType.ll_cas.ll_setStringValue(addr, ((Indirect_Type)jcasType).casFeatCode_indtype, v);}    
   
    
  //*--------------*
  //* Feature: penalty

  /** getter for penalty - gets Penalty value for this indirect STW
   * @generated
   * @return value of the feature 
   */
  public int getPenalty() {
    if (Indirect_Type.featOkTst && ((Indirect_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Indirect");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Indirect_Type)jcasType).casFeatCode_penalty);}
    
  /** setter for penalty - sets Penalty value for this indirect STW 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPenalty(int v) {
    if (Indirect_Type.featOkTst && ((Indirect_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Indirect");
    jcasType.ll_cas.ll_setIntValue(addr, ((Indirect_Type)jcasType).casFeatCode_penalty, v);}    
  }

    