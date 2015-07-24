package com.apiumtech.br.domains.service

import java.util.Date

/**
 * @author kate
 * @since 7/24/15.
 */

sealed trait ImageDTO extends Serializable {
  def id: Int
  def nombre: String
  def url: String
  def listaCoordenadas: Seq[String]
}

trait ServiceDTO extends Serializable {
  def id: Int
  def nombre: String
  def priority: Int
  def idTipoProducto: Int
  def objImgLarge: ImageDTO
  def objImgMedium: ImageDTO
  def objImgSecundaria: ImageDTO
  def difDateEnd: String
  def difDateDays: Int

  /** serialization getters **/
  override def toString = "[" + id + "] " + nombre
}

trait Service {
  def toDTO: ServiceDTO
}

sealed trait ServiceDefaultDTO extends Service with ServiceDTO {
  def toDTO = this
}

case class ConcreteService(id: Int, nombre: String, priority: Int,
                           idTipoProducto: Int, objImgLarge: ImageDTO,
                           objImgMedium: ImageDTO, objImgSecundaria: ImageDTO,
                           difDateEnd: String, difDateDays: Int) extends ServiceDefaultDTO {
}

object Service {
  def apply(id: Int, nombre: String, priority: Int,
            idTipoProducto: Int, objImgLarge: ImageDTO,
            objImgMedium: ImageDTO, objImgSecundaria: ImageDTO,
            difDateEnd: String, difDateDays: Int): ConcreteService = ConcreteService(id, nombre, priority, idTipoProducto, objImgLarge, objImgMedium, objImgSecundaria, difDateEnd, difDateDays)
}