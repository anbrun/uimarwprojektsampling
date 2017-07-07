

/* First created by JCasGen Thu Nov 19 14:35:15 CET 2015 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A sample from this text
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class Sample extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Sample.class);
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
  protected Sample() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Sample(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Sample(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Sample(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets ID of this sample
   * @generated
   * @return value of the feature 
   */
  public int getId() {
    if (Sample_Type.featOkTst && ((Sample_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "uimarwprojekt.types.Sample");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Sample_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets ID of this sample 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(int v) {
    if (Sample_Type.featOkTst && ((Sample_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "uimarwprojekt.types.Sample");
    jcasType.ll_cas.ll_setIntValue(addr, ((Sample_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: tok

  /** getter for tok - gets Token length of this sample
   * @generated
   * @return value of the feature 
   */
  public int getTok() {
    if (Sample_Type.featOkTst && ((Sample_Type)jcasType).casFeat_tok == null)
      jcasType.jcas.throwFeatMissing("tok", "uimarwprojekt.types.Sample");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Sample_Type)jcasType).casFeatCode_tok);}
    
  /** setter for tok - sets Token length of this sample 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTok(int v) {
    if (Sample_Type.featOkTst && ((Sample_Type)jcasType).casFeat_tok == null)
      jcasType.jcas.throwFeatMissing("tok", "uimarwprojekt.types.Sample");
    jcasType.ll_cas.ll_setIntValue(addr, ((Sample_Type)jcasType).casFeatCode_tok, v);}    
  }

    