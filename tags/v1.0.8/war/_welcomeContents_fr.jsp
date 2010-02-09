<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<div id="resume">
  <h2>Qu'est-ce que MemWords&nbsp;?</h2>
  <p>MemWords un gestionnaire de mots de passe en ligne gratuit.</p>
  <p>MemWords stocke vos mots de passe de mani�re s�re, prot�g�s par un unique mot de passe principal.</p>
  <p>O� que vous soyez, connectez-vous � MemWords avec votre mot de passe principal, et retrouvez tous les mots de passe que vous y avez stock�s.</p>
  <p style="font-size: x-large;"><stripes:link beanclass="com.googlecode.memwords.web.ScreenshotsActionBean">Jetez un coup d'oeil aux copies d'�cran&nbsp;!</stripes:link></p>
</div>
<div id="features">
  <h2>Points cl�s</h2>
  <ul>
    <li>Compl�tement gratuit, et libre.</li>
    <li>Cr�ez autant de compte que vous le d�sirez, contenant autant de cartes que vous le d�sirez.</li>
    <li>Trafic s�curis� par SSL.</li>
    <li>Toutes les donn�es sensibles sont encrypt�es en base de donn�es.</li>
    <li>Les ic�nes de vos sites web sont automatiquement trouv�es et utilis�es.</li>
    <li>G�n�ration al�atoire de mots de passe, suivant vos pr�f�rences.</li>
    <li>Traduit dans plusieurs langues (anglais et fran�ais pour le moment).</li>
    <li>Utilise JavaScript et AJAX pour un meilleur confort, mais fonctionne correctement sans JavaScript.</li>
  </ul>
</div>
<div id="faq">
  <h2>FAQ</h2>
  <div class="question">
    Est-ce s�curis�&nbsp;?
  </div>
  <div class="answer">
    Oui. Le trafic entre votre ordinateur et le serveur o� les mots de passe sont stock�s est prot�g� par SSL.
  </div>
  <div class="question">
    Et l'administrateur de l'application&nbsp;? S'il consulte la base de donn�es, il va y trouver mes mots de passe, non&nbsp;?
  </div>
  <div class="answer">
    Non. La plupart des informations dans la base de donn�es sont encrypt�es&nbsp;: les mots de passe, mais aussi les identifiants, les URLs, les notes, les ic�nes.
  </div>
  <div class="question">
    C'est encrypt�, mais l'administrateur doit forc�ment conna�tre la cl�, sinon les informations ne pourraient pas �tre d�crypt�es, n'est-ce pas&nbsp;?
  </div>
  <div class="answer">
    Non. La cl� utilis�e pour encrypter <em>vos</em> informations est prot�g�e par <em>votre</em> mot de passe principal, que vous �tes le seul � conna�tre.<br/>
    <a href="http://code.google.com/p/memwords/wiki/Security" class="external">Plus d'informations sur la s�curit� dans MemWords</a>
  </div>
  <div class="question">
    Quels sont les algorithmes utilis�s pour prot�ger mes mots de passe?
  </div>
  <div class="answer">
    MemWords utilise deux algorithmes cryptographiques&nbsp;: AES-128 and SHA-256.
  </div>
  <div class="question">
    Pourquoi ne pas utiliser AES-256?
  </div>
  <div class="answer">
    Premi�re raison&nbsp;: MemWords est un projet d�velopp� comme un hobby, h�berg� gratuitement par <a href="http://code.google.com/appengine/">Google App Engine</a>. Et Google App Engine ne supporte pas AES-256.<br/>
    Deuxi�me raison&nbsp;: AES-128 est suffisamment s�r. Votre mot de passe principal est le maillon le plus faible, quoi qu'il en soit. Si vous �tes vraiment craintif quant � vos mots de passe, ne les stockez que dans votre cerveau.
  </div>
  <div class="question">
    Que se passe-t-il si mon mot de passe principal est compromis&nbsp;?
  </div>
  <div class="answer">
    Pas de chance. Vous pouvez toujours le changer avant qu'il ne soit trop tard, ou d�truire votre compte. La s�curit� de votre compte
    d�pend de la force de votre mot de passe principal. Choisissez-en un compliqu�, et gardez-le pour vous.
  </div>
  <div class="question">
    Que se passe-t-il si j'oublie mon mot de passe principal&nbsp;?
  </div>
  <div class="answer">
    Pas de chance. MemWords est con�u afin que personne ne puisse, m�me en ayant acc�s � la base de donn�es,
    retrouver un mot de passe principal. A moins que vous ne retrouviez votre mot de passe principal,
    votre compte est donc destin� � vivre �ternellement, sans que personne ne puisse jamais y acc�der.
  </div>
  <div class="question">
    Garantissez-vous que mes informations ne seront jamais perdues&nbsp;?
  </div>
  <div class="answer">
    Non. MemWords est un projet d�velopp� comme un hobby. Je vous fournis l'application, et vous pouvez
    en obtenir le code source si vous le d�sirez. Mais vous l'utilisez � vos risques et p�rils.
  </div>
</div>