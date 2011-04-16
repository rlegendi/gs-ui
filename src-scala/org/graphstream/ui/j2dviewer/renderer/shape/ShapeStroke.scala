/*
 * Copyright 2006 - 2011 
 *     Julien Baudry	<julien.baudry@graphstream-project.org>
 *     Antoine Dutot	<antoine.dutot@graphstream-project.org>
 *     Yoann Pigné		<yoann.pigne@graphstream-project.org>
 *     Guilhelm Savin	<guilhelm.savin@graphstream-project.org>
 * 
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */
package org.graphstream.ui.j2dviewer.renderer.shape

import java.awt.{Stroke, BasicStroke, Color}
import org.graphstream.ui.graphicGraph.stylesheet.Style

abstract class ShapeStroke {
	def stroke( width:Double ):Stroke
}

object ShapeStroke {
	def strokeForArea( style:Style ):ShapeStroke = {
		import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.StrokeMode._
		style.getStrokeMode match {
			case PLAIN  => new PlainShapeStroke
			case DOTS   => new PlainShapeStroke //DotsShapeStroke
			case DASHES => new PlainShapeStroke //DashesShapeStroke
			case _      => null
		}
	}
 
	def strokeForConnectorFill( style:Style ):ShapeStroke = {
		import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.FillMode._
		style.getFillMode match {
			case PLAIN     => new PlainShapeStroke
			case DYN_PLAIN => new PlainShapeStroke
			case _         => new PlainShapeStroke
		}
	}
 
	def strokeForConnectorStroke( style:Style ):ShapeStroke = {
		strokeForArea( style )
	}
 
	def strokeColor( style:Style ):Color = {
		if( style.getStrokeMode != org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.StrokeMode.NONE ) {
			style.getStrokeColor( 0 )
		} else {
			null
		}
	}
 
	class PlainShapeStroke extends ShapeStroke {
		private[this] var oldWidth = 0.0
		private[this] var oldStroke:Stroke = null
		
		def stroke( width:Double ):Stroke = {
			if( width == oldWidth ) {
				if( oldStroke == null ) oldStroke = new BasicStroke( width.toFloat/*, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL*/ )	// WTF ??
				oldStroke
			} else {
				oldWidth  = width
				oldStroke = new BasicStroke( width.toFloat/*, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL*/ )
				oldStroke
			}
		}
	}
}