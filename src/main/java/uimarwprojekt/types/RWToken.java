

/* First created by JCasGen Mon Nov 10 17:36:24 CET 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class RWToken extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RWToken.class);
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
  protected RWToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RWToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RWToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RWToken(JCas jcas, int begin, int end) {
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
  //* Feature: lemma

  /** getter for lemma - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLemma() {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.RWToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(String v) {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.RWToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_lemma, v);}    
   
    
  //*--------------*
  //* Feature: pos

  /** getter for pos - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPos() {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "uimarwprojekt.types.RWToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_pos);}
    
  /** setter for pos - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPos(String v) {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "uimarwprojekt.types.RWToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_pos, v);}    
   
    
  //*--------------*
  //* Feature: rfpos

  /** getter for rfpos - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRfpos() {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_rfpos == null)
      jcasType.jcas.throwFeatMissing("rfpos", "uimarwprojekt.types.RWToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_rfpos);}
    
  /** setter for rfpos - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRfpos(String v) {
    if (RWToken_Type.featOkTst && ((RWToken_Type)jcasType).casFeat_rfpos == null)
      jcasType.jcas.throwFeatMissing("rfpos", "uimarwprojekt.types.RWToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((RWToken_Type)jcasType).casFeatCode_rfpos, v);}    
  }

    