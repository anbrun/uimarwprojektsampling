

/* First created by JCasGen Fri Oct 17 12:22:24 CEST 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Reported STW representation
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class Reported extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Reported.class);
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
  protected Reported() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Reported(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Reported(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Reported(JCas jcas, int begin, int end) {
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
    if (Reported_Type.featOkTst && ((Reported_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.Reported");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Reported_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(String v) {
    if (Reported_Type.featOkTst && ((Reported_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.Reported");
    jcasType.ll_cas.ll_setStringValue(addr, ((Reported_Type)jcasType).casFeatCode_lemma, v);}    
   
    
  //*--------------*
  //* Feature: penalty

  /** getter for penalty - gets 
   * @generated
   * @return value of the feature 
   */
  public int getPenalty() {
    if (Reported_Type.featOkTst && ((Reported_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Reported");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Reported_Type)jcasType).casFeatCode_penalty);}
    
  /** setter for penalty - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPenalty(int v) {
    if (Reported_Type.featOkTst && ((Reported_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Reported");
    jcasType.ll_cas.ll_setIntValue(addr, ((Reported_Type)jcasType).casFeatCode_penalty, v);}    
  }

    