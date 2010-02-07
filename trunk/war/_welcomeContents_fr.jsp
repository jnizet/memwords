<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<div id="resume">
  <h2>Qu'est-ce que MemWords&nbsp;?</h2>
  <p>MemWords un gestionnaire de mots de passe en ligne gratuit.</p>
  <p>MemWords stocke vos mots de passe de manière sûre, protégés par un unique mot de passe principal.</p>
  <p>Où que vous soyez, connectez-vous à MemWords avec votre mot de passe principal, et retrouvez tous les mots de passe que vous y avez stockés.</p>
  <p style="font-size: x-large;"><stripes:link beanclass="com.googlecode.memwords.web.ScreenshotsActionBean">Jetez un coup d'oeil aux copies d'écran&nbsp;!</stripes:link></p>
</div>
<div id="features">
  <h2>Points clés</h2>
  <ul>
    <li>Complètement gratuit, et libre.</li>
    <li>Créez autant de compte que vous le désirez, contenant autant de cartes que vous le désirez.</li>
    <li>Trafic sécurisé par SSL.</li>
    <li>Toutes les données sensibles sont encryptées en base de données.</li>
    <li>Les icônes de vos sites web sont automatiquement trouvées et utilisées.</li>
    <li>Génération aléatoire de mots de passe, suivant vos préférences.</li>
    <li>Traduit dans plusieurs langues (anglais et français pour le moment).</li>
    <li>Utilise JavaScript et AJAX pour un meilleur confort, mais fonctionne correctement sans JavaScript.</li>
  </ul>
</div>
<div id="faq">
  <h2>FAQ</h2>
  <div class="question">
    Est-ce sécurisé&nbsp;?
  </div>
  <div class="answer">
    Oui. Le trafic entre votre ordinateur et le serveur où les mots de passe sont stockés est protégé par SSL.
  </div>
  <div class="question">
    Et l'administrateur de l'application&nbsp;? S'il consulte la base de données, il va y trouver mes mots de passe, non&nbsp;?
  </div>
  <div class="answer">
    Non. La plupart des informations dans la base de données sont encryptées&nbsp;: les mots de passe, mais aussi les identifiants, les URLs, les notes, les icônes.
  </div>
  <div class="question">
    C'est encrypté, mais l'administrateur doit forcément connaître la clé, sinon les informations ne pourraient pas être décryptées, n'est-ce pas&nbsp;?
  </div>
  <div class="answer">
    Non. La clé utilisée pour encrypter <em>vos</em> informations est protégée par <em>votre</em> mot de passe principal, que vous êtes le seul à connaître.<br/>
    <a href="http://code.google.com/p/memwords/wiki/Security" class="external">Plus d'informations sur la sécurité dans MemWords</a>
  </div>
  <div class="question">
    Quels sont les algorithmes utilisés pour protéger mes mots de passe?
  </div>
  <div class="answer">
    MemWords utilise deux algorithmes cryptographiques&nbsp;: AES-128 and SHA-256.
  </div>
  <div class="question">
    Pourquoi ne pas utiliser AES-256?
  </div>
  <div class="answer">
    Première raison&nbsp;: MemWords est un projet développé comme un hobby, hébergé gratuitement par <a href="http://code.google.com/appengine/">Google App Engine</a>. Et Google App Engine ne supporte pas AES-256.<br/>
    Deuxième raison&nbsp;: AES-128 est suffisamment sûr. Votre mot de passe principal est le maillon le plus faible, quoi qu'il en soit. Si vous êtes vraiment craintif quant à vos mots de passe, ne les stockez que dans votre cerveau.
  </div>
  <div class="question">
    Que se passe-t-il si mon mot de passe principal est compromis&nbsp;?
  </div>
  <div class="answer">
    Pas de chance. Vous pouvez toujours le changer avant qu'il ne soit trop tard, ou détruire votre compte. La sécurité de votre compte
    dépend de la force de votre mot de passe principal. Choisissez-en un compliqué, et gardez-le pour vous.
  </div>
  <div class="question">
    Que se passe-t-il si j'oublie mon mot de passe principal&nbsp;?
  </div>
  <div class="answer">
    Pas de chance. MemWords est conçu afin que personne ne puisse, même en ayant accès à la base de données,
    retrouver un mot de passe principal. A moins que vous ne retrouviez votre mot de passe principal,
    votre compte est donc destiné à vivre éternellement, sans que personne ne puisse jamais y accéder.
  </div>
  <div class="question">
    Garantissez-vous que mes informations ne seront jamais perdues&nbsp;?
  </div>
  <div class="answer">
    Non. MemWords est un projet développé comme un hobby. Je vous fournis l'application, et vous pouvez
    en obtenir le code source si vous le désirez. Mais vous l'utilisez à vos risques et périls.
  </div>
</div>