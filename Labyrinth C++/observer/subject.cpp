/*!
 * \file subject.cpp
 * \brief Implémentation de la classe nvs::Subject.
 */

#include "subject.h"
#include "observer.h"

namespace nvs
{

void Subject::registerObserver(Observer * observer)
{
    observers_.insert(observer);
}

void Subject::unregisterObserver(Observer * observer)
{
    observers_.erase(observer);
}

void Subject::notifyObservers() const
{
    for (Observer * observer : observers_)
    {
        observer->update(this);
    }
}

} // namespace nvs
