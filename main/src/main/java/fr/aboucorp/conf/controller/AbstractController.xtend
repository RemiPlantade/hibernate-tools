package fr.aboucorp.conf.controller

import java.util.List
import api_conf.conf.model.ApiConf

abstract class AbstractController {
	MainController mainCtrl

	def MainController getMainCtrl() {
		return mainCtrl
	}

	def void setMainCtrl(MainController mainCtrl) {
		this.mainCtrl = mainCtrl
	}

	def abstract void checkInfo()

	def abstract List<ApiConf> getAllApiConf()

	def abstract void updateValues()

}
