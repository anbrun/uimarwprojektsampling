

/* First created by JCasGen Wed Jun 18 14:17:14 CEST 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A word that indictates a speech, thought or writing event
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class STWWord extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(STWWord.class);
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
  protected STWWord() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public STWWord(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public STWWord(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public STWWord(JCas jcas, int begin, int end) {
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

  /** getter for lemma - gets lemma
   * @generated
   * @return value of the feature 
   */
  public String getLemma() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets lemma 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(String v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_lemma, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets source
   * @generated
   * @return value of the feature 
   */
  public String getSource() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets source 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSource(String v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_source, v);}    
   
    
  //*--------------*
  //* Feature: penalty

  /** getter for penalty - gets penalty
   * @generated
   * @return value of the feature 
   */
  public int getPenalty() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getIntValue(addr, ((STWWord_Type)jcasType).casFeatCode_penalty);}
    
  /** setter for penalty - sets penalty 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPenalty(int v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_penalty == null)
      jcasType.jcas.throwFeatMissing("penalty", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setIntValue(addr, ((STWWord_Type)jcasType).casFeatCode_penalty, v);}    
   
    
  //*--------------*
  //* Feature: category

  /** getter for category - gets category
   * @generated
   * @return value of the feature 
   */
  public String getCategory() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_category);}
    
  /** setter for category - sets category 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCategory(String v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_category, v);}    
   
    
  //*--------------*
  //* Feature: marker

  /** getter for marker - gets marker
   * @generated
   * @return value of the feature 
   */
  public String getMarker() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_marker == null)
      jcasType.jcas.throwFeatMissing("marker", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_marker);}
    
  /** setter for marker - sets marker 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMarker(String v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_marker == null)
      jcasType.jcas.throwFeatMissing("marker", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setStringValue(addr, ((STWWord_Type)jcasType).casFeatCode_marker, v);}    
   
    
  //*--------------*
  //* Feature: frequency

  /** getter for frequency - gets frequency
   * @generated
   * @return value of the feature 
   */
  public int getFrequency() {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_frequency == null)
      jcasType.jcas.throwFeatMissing("frequency", "uimarwprojekt.types.STWWord");
    return jcasType.ll_cas.ll_getIntValue(addr, ((STWWord_Type)jcasType).casFeatCode_frequency);}
    
  /** setter for frequency - sets frequency 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFrequency(int v) {
    if (STWWord_Type.featOkTst && ((STWWord_Type)jcasType).casFeat_frequency == null)
      jcasType.jcas.throwFeatMissing("frequency", "uimarwprojekt.types.STWWord");
    jcasType.ll_cas.ll_setIntValue(addr, ((STWWord_Type)jcasType).casFeatCode_frequency, v);}    
  }

    