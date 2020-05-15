/*!
 * \file subject.h
 * \brief Définition de la classe nvs::Subject.
 */

#ifndef __SUBJECT__H__
#define __SUBJECT__H__

#include <set>

/*!
 * \mainpage Modèle de conception Observateur
 *
 * L'implémentation du _desing pattern_
 * [Observer](https://en.wikipedia.org/wiki/Observer_pattern)
 * donnée ici est fidèle au modèle du
 * [Gang of Four](https://en.wikipedia.org/wiki/Design_Patterns). En
 * particulier, il n'y est pas fait usage d'une classe correspondant
 * aux _événements_, mais uniquement d'une classe
 * _source d'événements_, le sujet nvs::Subject, et d'une classe
 * _écouteur d'événements_, l'observateur nvs::Observer.
 *
 * Dans ses mises en œuvre effective, ce modèle est très souvent
 * étoffé par l'ajout de classes d'événement ou par la multiplication
 * des méthodes de notification.
 * Rien, cependant, de ce que ces complexifications du modèle
 * rendent plus facile aux utilisateurs, n'est hors de portée du
 * modèle simple donné ici. Les développements du modèle de conception
 * peuvent en effet être reportées sur les classes concrètes qui
 * l'implémentent. Remarquez que si le contenu de ce paragraphe
 * vous semble abscons, oubliez-le provisoirement. On en reparle
 * lors de la
 * [gestion des événements avec Qt](https://doc.qt.io/qt-5/eventsandfilters.html).
 *
 */

/*!
 * \brief Espace de nom de Nicolas Vansteenkiste.
 */
namespace nvs
{

class Observer;

/*!
 * \brief Classe de base de tout "sujet d'observation".
 *
 * Classe dont dérive toute source d'événement (ou "sujet d'observation")
 * du modèle de conception "Observateur / SujetDObservation".
 *
 * \sa Observer.
 */
class Subject
{
  public:

    /*!
     * \brief Destructeur virtuel par défaut car utilisation polymorphique.
     */
    virtual ~Subject() = default;

    /*!
     * \brief Constructeur par recopie par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Subject(const Subject &) = default;

    /*!
     * \brief Constructeur par déplacement par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Subject(Subject &&) = default;

    /*!
     * \brief Opérateur d'assignation par recopie par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Subject & operator=(const Subject &) = default;

    /*!
     * \brief Opérateur d'assignation par déplacement par défaut.
     *
     * Le destructeur virtuel par défaut a des effets en cascade.
     *
     * \see http://stackoverflow.com/q/33957037
     * \see http://scottmeyers.blogspot.de/2014/03/a-concern-about-rule-of-zero.html
     * \see https://blog.feabhas.com/2015/11/becoming-a-rule-of-zero-hero/
     */
    Subject & operator=(Subject &&) = default;

    /*!
     * \brief Méthode permettant à un observateur de s'enregistrer comme
     * écouteur du sujet d'observation.
     *
     * \param observer un pointeur vers le candidat observateur.
     */
    virtual void registerObserver(Observer * observer) final;

    /*!
     * \brief Méthode permettant à un observateur de se retirer de la liste
     * des écouteurs patentés du sujet d'observation.
     *
     * \param observer l'adresse de l'observateur désintéressé.
     */
    virtual void unregisterObserver(Observer * observer) final;

  protected:

    /*!
     * \brief Constructeur protégé pour éviter l'instanciation
     *        hors héritage.
     */
    Subject() = default;

    /*!
     * \brief Méthode qui se charge de prévenir les observateurs d'un
     * changement d'état du sujet d'observation, en invoquant leur
     * méthode Observer::update().
     *
     * \sa Observer::update(const Subject *).
     */
    virtual void notifyObservers() const final;

  protected:

    /*!
     * \brief L'ensemble d'observateurs enregistrés.
     */
    std::set<Observer *> observers_ { };
};

} // namespace nvs

#endif
