

/* First created by JCasGen Sat Dec 20 16:26:52 CET 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * XML source: C:/Users/Annelen/Documents/eclipse-luna-workspace/uimarwprojektsampling/src/main/java/uimarwprojekt/types/RWProjektTypeSystem.xml
 * @generated */
public class FreeIndirect extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FreeIndirect.class);
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
  protected FreeIndirect() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FreeIndirect(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FreeIndirect(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FreeIndirect(JCas jcas, int begin, int end) {
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
  //* Feature: weight

  /** getter for weight - gets 
   * @generated
   * @return value of the feature 
   */
  public int getWeight() {
    if (FreeIndirect_Type.featOkTst && ((FreeIndirect_Type)jcasType).casFeat_weight == null)
      jcasType.jcas.throwFeatMissing("weight", "uimarwprojekt.types.FreeIndirect");
    return jcasType.ll_cas.ll_getIntValue(addr, ((FreeIndirect_Type)jcasType).casFeatCode_weight);}
    
  /** setter for weight - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWeight(int v) {
    if (FreeIndirect_Type.featOkTst && ((FreeIndirect_Type)jcasType).casFeat_weight == null)
      jcasType.jcas.throwFeatMissing("weight", "uimarwprojekt.types.FreeIndirect");
    jcasType.ll_cas.ll_setIntValue(addr, ((FreeIndirect_Type)jcasType).casFeatCode_weight, v);}    
  }

    