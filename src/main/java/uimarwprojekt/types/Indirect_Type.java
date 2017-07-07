
/* First created by JCasGen Thu Oct 16 11:34:01 CEST 2014 */
package uimarwprojekt.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Indirect speech, thought or writing
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * @generated */
public class Indirect_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Indirect_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Indirect_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Indirect(addr, Indirect_Type.this);
  			   Indirect_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Indirect(addr, Indirect_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Indirect.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("uimarwprojekt.types.Indirect");



  /** @generated */
  final Feature casFeat_indtype;
  /** @generated */
  final int     casFeatCode_indtype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getIndtype(int addr) {
        if (featOkTst && casFeat_indtype == null)
      jcas.throwFeatMissing("indtype", "uimarwprojekt.types.Indirect");
    return ll_cas.ll_getStringValue(addr, casFeatCode_indtype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIndtype(int addr, String v) {
        if (featOkTst && casFeat_indtype == null)
      jcas.throwFeatMissing("indtype", "uimarwprojekt.types.Indirect");
    ll_cas.ll_setStringValue(addr, casFeatCode_indtype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_penalty;
  /** @generated */
  final int     casFeatCode_penalty;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPenalty(int addr) {
        if (featOkTst && casFeat_penalty == null)
      jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Indirect");
    return ll_cas.ll_getIntValue(addr, casFeatCode_penalty);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPenalty(int addr, int v) {
        if (featOkTst && casFeat_penalty == null)
      jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Indirect");
    ll_cas.ll_setIntValue(addr, casFeatCode_penalty, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Indirect_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_indtype = jcas.getRequiredFeatureDE(casType, "indtype", "uima.cas.String", featOkTst);
    casFeatCode_indtype  = (null == casFeat_indtype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_indtype).getCode();

 
    casFeat_penalty = jcas.getRequiredFeatureDE(casType, "penalty", "uima.cas.Integer", featOkTst);
    casFeatCode_penalty  = (null == casFeat_penalty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_penalty).getCode();

  }
}



    