package pe.greenminds.ecomind_backend.learning.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;

@Component
public class LearningSeedApplicationReadyEventHandler {

    private final EducationalMaterialRepository repository;
    private final boolean enabled;

    public LearningSeedApplicationReadyEventHandler(
            EducationalMaterialRepository repository,
            @Value("${learning.seed.enabled}") boolean enabled
    ) {
        this.repository = repository;
        this.enabled = enabled;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        if (!enabled) return;
        if (repository.findAll().isEmpty()) {
            seed();
        }
    }

    private void seed() {
        repository.save(new EducationalMaterial(
                "♻️ Reciclaje Básico",
                "Aprende los fundamentos del reciclaje y cómo separar correctamente tus residuos.",
                "El reciclaje es el proceso de convertir materiales de desecho en nuevos materiales y objetos. Esto reduce el consumo de materias primas, el uso de energía, la contaminación del aire y del agua.\n\n## ¿Por qué reciclar?\n- Conserva los recursos naturales\n- Ahorra energía\n- Reduce los residuos en vertederos\n- Crea empleos\n\n## ¿Qué se puede reciclar?\n- Papel y cartón\n- Botellas y frascos de vidrio\n- Latas y papel de aluminio\n- Botellas y envases de plástico\n\n## Consejos para reciclar mejor\n1. Enjuaga los envases antes de reciclarlos\n2. Mantén los reciclables sueltos (no en bolsas)\n3. Revisa las normas locales de reciclaje",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                null,
                5
        ));

        repository.save(new EducationalMaterial(
                "💧 Ahorro de Agua en Casa",
                "Formas sencillas de reducir el consumo de agua en tu vida diaria.",
                "El agua es uno de nuestros recursos más preciados. Con el cambio climático afectando la disponibilidad de agua, conservarla en casa nunca ha sido más importante.\n\n## ¿Por qué ahorrar agua?\n- Proteger los ecosistemas naturales\n- Reducir la energía utilizada en el tratamiento del agua\n- Ahorrar dinero en las facturas\n- Asegurar agua para futuras generaciones\n\n## Formas fáciles de ahorrar agua\n- Repara las llaves que gotean\n- Toma duchas más cortas\n- Cierra la llave mientras te cepillas los dientes\n- Usa una escoba en lugar de manguera para limpiar\n- Recolecta agua de lluvia para las plantas\n\n## ¿Sabías que?\n¡Una sola llave que gotea puede desperdiciar más de 3,000 litros de agua al año!",
                MaterialType.TEXT,
                MaterialCategory.WATER,
                null,
                4
        ));

        repository.save(new EducationalMaterial(
                "🔋 Energías Renovables",
                "Una visión general de la energía solar, eólica y otras fuentes de energía limpia.",
                "La energía renovable proviene de fuentes naturales que se reponen constantemente. A diferencia de los combustibles fósiles, la energía renovable produce pocas o ninguna emisión de gases de efecto invernadero.\n\n## Tipos de Energía Renovable\n\n### Energía Solar\nConvierte la luz solar en electricidad mediante paneles fotovoltaicos.\n\n### Energía Eólica\nUtiliza turbinas eólicas para generar electricidad.\n\n### Energía Hidroeléctrica\nGenera electricidad a partir del agua en movimiento.\n\n### Energía Geotérmica\nUtiliza el calor del interior de la Tierra.\n\n## Beneficios\n- Reduce las emisiones de carbono\n- Crea independencia energética\n- Menores costos a largo plazo\n- Sostenible y abundante",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                null,
                6
        ));

        repository.save(new EducationalMaterial(
                "🌍 Cambio Climático",
                "Comprende las causas y efectos del cambio climático global.",
                "El cambio climático se refiere a cambios a largo plazo en las temperaturas y patrones climáticos. Aunque los factores naturales influyen, las actividades humanas han sido el principal impulsor desde el siglo XIX.\n\n## Principales Causas\n- Quema de combustibles fósiles\n- Deforestación\n- Agricultura industrial\n- Descomposición de residuos\n\n## Efectos que ya vemos\n- Aumento de temperaturas globales\n- Eventos climáticos extremos más frecuentes\n- Deshielo de glaciares y aumento del nivel del mar\n- Alteración de ecosistemas\n\n## Qué puedes hacer\n- Reduce el consumo de energía\n- Usa transporte público o bicicleta\n- Consume menos carne\n- Apoya las energías renovables\n- Planta árboles",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                null,
                7
        ));

        repository.save(new EducationalMaterial(
                "🗑️ Compostaje en Casa",
                "Convierte tus residuos de cocina en abono rico en nutrientes para tu jardín.",
                "El compostaje es la forma que tiene la naturaleza de reciclar residuos orgánicos y convertirlos en fertilizante valioso. Es fácil de empezar y marca una gran diferencia para el medio ambiente.\n\n## ¿Qué puedes compostar?\n- Restos de frutas y verduras\n- Posos de café y filtros\n- Cáscaras de huevo\n- Recortes de césped y hojas\n- Papel triturado\n\n## ¿Qué NO compostar?\n- Carne y productos lácteos\n- Alimentos grasosos\n- Plantas enfermas\n- Excrementos de mascotas\n\n## Cómo empezar\n1. Elige un contenedor o lugar para el compost\n2. Alterna capas de material verde y marrón\n3. Mantén la humedad adecuada\n4. Voltea regularmente para airear\n5. Espera 2 a 6 meses para tener compost listo",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                null,
                5
        ));

        repository.save(new EducationalMaterial(
                "🚰 Cómo Funciona el Tratamiento del Agua",
                "Infografía que muestra el recorrido del agua desde su origen hasta tu grifo.",
                "El tratamiento del agua es un proceso fascinante que garantiza que el agua que sale de tu grifo sea segura para beber.\n\nPaso 1: Captación - El agua se recolecta de ríos, lagos o embalses.\nPaso 2: Cribado - Se eliminan residuos grandes como ramas y hojas.\nPaso 3: Coagulación - Se añaden químicos para agrupar partículas pequeñas.\nPaso 4: Sedimentación - Los grupos se depositan en el fondo.\nPaso 5: Filtración - El agua pasa por filtros de arena y carbón.\nPaso 6: Desinfección - El cloro o luz UV eliminan microorganismos.\nPaso 7: Distribución - El agua limpia viaja por tuberías a tu hogar.\n\nDato curioso: ¡Una planta de tratamiento puede procesar millones de litros al día!",
                MaterialType.INFOGRAPHIC,
                MaterialCategory.WATER,
                null,
                3
        ));

        repository.save(new EducationalMaterial(
                "🏠 Consejos de Eficiencia Energética",
                "Reduce tus facturas de energía y tu huella de carbono con estos consejos prácticos.",
                "Hacer tu hogar más eficiente energéticamente es una de las mejores formas de ahorrar dinero y ayudar al medio ambiente.\n\n## Acciones Rápidas\n- Cambia a bombillas LED (usan 75% menos energía)\n- Desconecta aparatos electrónicos cuando no los uses\n- Usa un termostato programable\n- Sella corrientes de aire en puertas y ventanas\n\n## Inversiones Medias\n- Añade aislamiento al ático y paredes\n- Instala ventanas eficientes\n- Actualiza a electrodomésticos ENERGY STAR\n\n## Soluciones a Largo Plazo\n- Instala paneles solares\n- Cambia a una bomba de calor\n- Considera un techo verde\n\nUn hogar típico puede ahorrar 25-30% en facturas de energía implementando estas medidas.",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                null,
                4
        ));

        repository.save(new EducationalMaterial(
                "🌿 Biodiversidad y Tú",
                "Por qué la biodiversidad es importante y cómo protegerla en tu comunidad.",
                "La biodiversidad - la variedad de vida en la Tierra - es esencial para la salud de nuestro planeta y nuestro bienestar.\n\n## Por qué es importante la biodiversidad\n- Proporciona alimentos, agua limpia y medicinas\n- Apoya la polinización de cultivos\n- Regula el clima\n- Protege contra desastres naturales\n\n## Amenazas a la Biodiversidad\n- Destrucción de hábitats\n- Contaminación\n- Especies invasoras\n- Cambio climático\n- Sobreexplotación\n\n## Cómo puedes ayudar\n- Planta especies nativas en tu jardín\n- Evita los pesticidas\n- Apoya los esfuerzos de conservación locales\n- Reduce tu huella de carbono\n- Educa a otros sobre la biodiversidad",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                null,
                6
        ));

        repository.save(new EducationalMaterial(
                "♻️ Crisis del Plástico",
                "Video que explica el impacto de la contaminación por plástico y las soluciones.",
                "La contaminación por plástico es uno de los problemas ambientales más urgentes de nuestro tiempo. Cada año, 8 millones de toneladas de plástico llegan a nuestros océanos.\n\n## El Problema\n- Solo el 9% del plástico producido ha sido reciclado\n- 12 millones de toneladas de plástico entran a los océanos anualmente\n- Para 2050, podría haber más plástico que peces en el mar\n- Se han encontrado microplásticos en agua potable, alimentos y sangre humana\n\n## Soluciones\n1. Reduce los plásticos de un solo uso\n2. Elige alternativas reutilizables\n3. Apoya leyes que prohíban plásticos innecesarios\n4. Participa en jornadas de limpieza\n5. Promueve la responsabilidad del productor\n\n## Qué puedes hacer hoy\nComienza por llevar una botella reutilizable, bolsa de compras y vaso para café.",
                MaterialType.VIDEO,
                MaterialCategory.RECYCLE,
                null,
                8
        ));

        repository.save(new EducationalMaterial(
                "💡 Revolución de la Energía Verde",
                "Video que muestra las innovaciones en tecnología de energía renovable.",
                "La revolución de la energía verde está transformando la forma en que alimentamos nuestro mundo. Desde granjas solares flotantes hasta turbinas eólicas de nueva generación, la innovación se acelera.\n\n## Tecnologías Innovadoras\n- Celdas solares de perovskita (más baratas, más eficientes)\n- Turbinas eólicas flotantes (acceden a aguas más profundas)\n- Hidrógeno verde (combustible limpio para la industria)\n- Centrales eléctricas virtuales (baterías domésticas en red)\n\n## Progreso Global\n- La energía renovable ya es más barata que el carbón\n- Más del 30% de la electricidad global proviene de renovables\n- La energía solar crece más rápido que cualquier otra fuente\n- Muchos países buscan llegar al 100% renovable para 2050\n\n## El Futuro es Brillante\nLa transición a la energía renovable no solo es necesaria, ya está ocurriendo. Y tú puedes ser parte de ella.",
                MaterialType.VIDEO,
                MaterialCategory.ENERGY,
                null,
                10
        ));
    }
}
