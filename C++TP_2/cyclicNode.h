// -*- c++ -*-
#ifndef _DataStructure_CyclicNode_H
#define _DataStructure_CyclicNode_H

namespace DataStructure
{
	/** Noeud cyclique.

	Ce noeud cyclique est reboucl� sur lui-m�me par d�faut ou peut �tre
	utilis� pour repr�senter des listes cycliques.
	@param T Le type de donn�e contenu dans ce noeud
	@warning Le type T doit poss�der un constructeur par d�faut et un constructeur de copie
	*/
	template <class T>
	class cyclicNode
	{
	protected:
		/** Le noeud pr�c�dent */
		cyclicNode<T> * m_previous;
		/** Le noeud suivant */
		cyclicNode<T> * m_next;
		/** La donn�e contenue dans ce noeud */
		T		    m_bloc;

	protected:
		void lightDetach()
		{
			m_previous->m_next = m_next;
			m_next->m_previous = m_previous;
		}

	public:
		/** Constructeur par d�faut */
		cyclicNode()
			: m_previous(this), m_next(this) { }

		/** Constructeur avec initialisation de la donn�e */
		cyclicNode(T const & data)
			: m_previous(this), m_next(this), m_bloc(data) { }

		/** R�cup�ration du noeud pr�d�cesseur */
		cyclicNode<T> * previous() const
		{
			return m_previous;
		}

		/** R�cup�ration du noeud suivant */
		cyclicNode<T> * next() const
		{
			return m_next;
		}

		/** R�cup�ration de la donn�e contenue dans ce noeud */
		const T & data() const
		{
			return m_bloc;
		}

		/** R�cup�ration de la donn�e contenue dans ce noeud */
		T & data()
		{
			return m_bloc;
		}

		/** D�croche ce noeud de la structure cyclique � laquelle il appartient */
		void detach()
		{
			lightDetach();
			m_previous = this;
			m_next = this;
		}
		//
		/** Ins�re un noeud avant ce noeud
		@param node Le noeud � ins�rer
		@warning Le noeud est d�croch� de sa structure courante
		*/
		void insertBefore(cyclicNode<T> * node)
		{
			node->lightDetach();
			m_previous->m_next = node;
			node->m_previous = m_previous;
			node->m_next = this;
			m_previous = node;
		}

		/** Ins�re un noeud apr�s ce noeud
		@param node Le noeud � ins�rer
		@warning Le noeud est d�croch� de sa structure courante
		*/
		void insertAfter(cyclicNode<T> * node)
		{
			node->lightDetach();
			m_next->m_previous = node;
			node->m_previous = this;
			node->m_next = m_next;
			m_next = node;
		}

		/** Destructeur
		Ce destructeur d�tache le noeud de sa structure courante.
		*/
		~cyclicNode()
		{
			detach();
		}
	};
}

#endif 
