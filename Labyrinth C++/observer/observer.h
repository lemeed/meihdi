/*!
 * \file observer.h
 * \brief Définition de la classe nvs::Observer.
 */

#ifndef __OBSERVER__H__
#define __OBSERVER__H__

/*!
 * \brief Espace de nom de Nicolas Vansteenkiste.
 */
namespace nvs
{

class Subject;

/*!
 * \brief Classe abstraite de base de tout observateur.
 *
 * Classe dont dérive tout écouteur (ou "observateur") du modèle de
 * conception "Observateur / SujetDObservation".
 *
 * \sa Subject.
 * \see https://en.wikipedia.org/wiki/Observer_pattern
 */

class Observer
{
  public:

    /*!
     * \brief Méthode virtuelle pure que chaque observateur concret doit
     * implémenter : c'est cette méthode qu'appelle le sujet observé lors
     * d'une notification.
     *
     * \param subject le sujet d'observation qui notifie un changement.
     * \see Subject::notifyObservers().
     */
    virtual void update(const Subject * subject) = 0;

    /*!
     * \brief Destructeur virtuel par défaut car utilisation polymorphique.
     */
    virtual ~Observer() = default;

    /*!
     * \brief Constructeur par recopie par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Observer(const Observer &) = default;

    /*!
     * \brief Constructeur par déplacement par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Observer(Observer &&) = default;

    /*!
     * \brief Opérateur d'assignation par recopie par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Observer & operator=(const Observer &) = default;

    /*!
     * \brief Opérateur d'assignation par déplacement par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Observer & operator=(Observer &&) = default;

  protected:

    /*!
     * \brief Constructeur protégé pour éviter l'instanciation
     *        hors héritage.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Observer() = default;
};

} // namespace nvs

#endif
