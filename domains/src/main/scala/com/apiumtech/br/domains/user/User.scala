package com.apiumtech.br.domains.user

import java.util.Date

/**
 * @author kevin 
 * @since 7/16/15.
 */

trait UserDTO extends Serializable {
  def name: String
  def isRegistered: Boolean

  /** serialization getters **/
  def getName = name
}

trait User {
  def registered: User
  def toDTO: UserDTO
}

sealed trait UserDefaultDTO extends User with UserDTO {
  def toDTO = this
}

case class UnregisteredUser(name: String) extends UserDefaultDTO {
  def isRegistered = false
  def registered = RegisteredUser(name, new Date)
}

case class RegisteredUser(name: String, registrationDate: Date) extends UserDefaultDTO {
  def isRegistered = true
  def registered = this
}

object User {
  def apply(name: String): UnregisteredUser = UnregisteredUser(name)
  def apply(name: String, registrationDate: Date): RegisteredUser = RegisteredUser(name, registrationDate)
}