package org.lapoderosa.app.util;

import java.util.ArrayList;

public class UtilsReport {
    public static ArrayList<String> getVariables() {
        ArrayList<String> variables = new ArrayList<String>();
        //variables.add("id");
        //allanamiento
        variables.add("usu_orden_allanamiento");
        variables.add("usu_agresion_allanamiento");
        variables.add("usu_pertenencias_allanamiento");
        variables.add("usu_omision_pertenencias");
        variables.add("usu_detenidos_allanamiento");
        variables.add("usu_duracion_allanamiento");
        variables.add("usu_esposados");
        variables.add("usu_posicion_allanamiento");


        //entrevistado
        variables.add("usu_parentesco_entrevistado");

        //entrevistador
        variables.add("usu_nombre");
        variables.add("usu_apellido");
        variables.add("usu_asamblea");
        variables.add("usu_fecha");

        //fuerzas_intervinientes
        variables.add("usu_fuerzas_intervinientes");
        variables.add("usu_cantidad_agentes");
        variables.add("usu_nombres_agentes");
        variables.add("usu_apodos");
        variables.add("usu_cantidad_vehiculos");
        variables.add("usu_num_movil");
        variables.add("usu_dominio");
        variables.add("usu_conducta_agentes");

        //hechi_policial
        variables.add("usu_dia_hecho");
        variables.add("usu_hora_hecho");
        variables.add("usu_ubicacion_hecho");
        variables.add("usu_cuantos_acompañan");
        variables.add("usu_cual_lugar");
        variables.add("usu_provincia_hecho");
        variables.add("usu_pais_hecho");

        //modalidad_detencion
        variables.add("usu_posicion_detenido");
        variables.add("usu_tiempo_detenido");

        //omision_actuar
        variables.add("usu_medios_de_asistencia");
        variables.add("usu_a_quien_asistencia");
        variables.add("usu_denuncia_rechazada");
        variables.add("usu_violentado");
        variables.add("usu_denuncia_final");

        //resultado_investigacion
        variables.add("usu_resultado_investigacion");
        variables.add("usu_trabajan_los_oficiales");


        variables.add("usu_traslado");
        variables.add("usu_comisaria");
        variables.add("usu_esposado");


        return variables;
    }

    public static ArrayList<String> getCategorias() {
        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("allanamiento");
        categorias.add("caracteristicasProcedimiento");
        categorias.add("entrevistado");
        categorias.add("entrevistador");
        categorias.add("fuerzasIntervinientes");
        categorias.add("hechoPolicial");
        categorias.add("modalidadDetencion");
        categorias.add("omisionActuar");
        categorias.add("resultadoInvestigacion");
        categorias.add("traslado");
        categorias.add("victima");
        return categorias;
    }

    /*
        public static ArrayList<String> getVictima(){
            ArrayList<String> variables = new ArrayList<String>();
            //victima
            variables.add("usu_nombre_victima");
            variables.add("usu_apellido_victima");
            variables.add("usu_genero_victima");
            variables.add("usu_edad_victima");
            variables.add("usu_nacionalidad_victima");
            variables.add("usu_documento_victima");
            variables.add("usu_direccion_victima");
            variables.add("usu_barrio_victima");
            variables.add("usu_telefono_victima");
            return variables;
        }
      */
    public static ArrayList<String> getVictima() {
        ArrayList<String> variables = new ArrayList<String>();
        //victima
        variables.add("nombre");
        variables.add("usu_apellido");
        variables.add("usu_genero_victima");
        variables.add("usu_edad_victima");
        variables.add("usu_nacionalidad_victima");
        variables.add("usu_documento_victima");
        variables.add("usu_direccion_victima");
        variables.add("usu_barrio_victima");
        variables.add("usu_telefono_victima");
        return variables;
    }
}
