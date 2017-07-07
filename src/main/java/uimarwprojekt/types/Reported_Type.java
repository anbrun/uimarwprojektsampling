
/* First created by JCasGen Fri Oct 17 12:22:24 CEST 2014 */
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

/** Reported STW representation
 * Updated by JCasGen Wed Nov 25 16:47:00 CET 2015
 * @generated */
public class Reported_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Reported_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Reported_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Reported(addr, Reported_Type.this);
  			   Reported_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Reported(addr, Reported_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Reported.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("uimarwprojekt.types.Reported");
 
  /** @generated */
  final Feature casFeat_lemma;
  /** @generated */
  final int     casFeatCode_lemma;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLemma(int addr) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "uimarwprojekt.types.Reported");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lemma);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLemma(int addr, String v) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "uimarwprojekt.types.Reported");
    ll_cas.ll_setStringValue(addr, casFeatCode_lemma, v);}
    
  
 
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
      jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Reported");
    return ll_cas.ll_getIntValue(addr, casFeatCode_penalty);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPenalty(int addr, int v) {
        if (featOkTst && casFeat_penalty == null)
      jcas.throwFeatMissing("penalty", "uimarwprojekt.types.Reported");
    ll_cas.ll_setIntValue(addr, casFeatCode_penalty, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
   * @generated
   * @param jcas JCas
   * @param casType Type 
   */
  public Reported_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lemma = jcas.getRequiredFeatureDE(casType, "lemma", "uima.cas.String", featOkTst);
    casFeatCode_lemma  = (null == casFeat_lemma) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemma).getCode();

 
    casFeat_penalty = jcas.getRequiredFeatureDE(casType, "penalty", "uima.cas.Integer", featOkTst);
    casFeatCode_penalty  = (null == casFeat_penalty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_penalty).getCode();

  }
}



    