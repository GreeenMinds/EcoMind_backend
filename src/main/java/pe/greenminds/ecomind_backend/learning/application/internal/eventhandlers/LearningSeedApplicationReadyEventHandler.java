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
        var existing = repository.findAll();
        if (!existing.isEmpty()) {
            existing.forEach(m -> repository.deleteById(m.getId()));
        }
        seed();
    }

    private void seed() {
        // ============ SPANISH MATERIALS ============
        repository.save(new EducationalMaterial(
                "♻️ Reciclaje Básico",
                "Aprende los fundamentos del reciclaje y cómo separar correctamente tus residuos.",
                "El reciclaje es el proceso de convertir materiales de desecho en nuevos materiales y objetos. Esto reduce el consumo de materias primas, el uso de energía, la contaminación del aire y del agua.\n\n## ¿Por qué reciclar?\n- Conserva los recursos naturales\n- Ahorra energía\n- Reduce los residuos en vertederos\n- Crea empleos\n\n## ¿Qué se puede reciclar?\n- Papel y cartón\n- Botellas y frascos de vidrio\n- Latas y papel de aluminio\n- Botellas y envases de plástico\n\n## Consejos para reciclar mejor\n1. Enjuaga los envases antes de reciclarlos\n2. Mantén los reciclables sueltos (no en bolsas)\n3. Revisa las normas locales de reciclaje",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b?w=600",
                5,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "💧 Ahorro de Agua en Casa",
                "Formas sencillas de reducir el consumo de agua en tu vida diaria.",
                "El agua es uno de nuestros recursos más preciados. Con el cambio climático afectando la disponibilidad de agua, conservarla en casa nunca ha sido más importante.\n\n## ¿Por qué ahorrar agua?\n- Proteger los ecosistemas naturales\n- Reducir la energía utilizada en el tratamiento del agua\n- Ahorrar dinero en las facturas\n- Asegurar agua para futuras generaciones\n\n## Formas fáciles de ahorrar agua\n- Repara las llaves que gotean\n- Toma duchas más cortas\n- Cierra la llave mientras te cepillas los dientes\n- Usa una escoba en lugar de manguera para limpiar\n- Recolecta agua de lluvia para las plantas\n\n## ¿Sabías que?\n¡Una sola llave que gotea puede desperdiciar más de 3,000 litros de agua al año!",
                MaterialType.TEXT,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1581092160562-40aa08e78837?w=600",
                4,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🔋 Energías Renovables",
                "Una visión general de la energía solar, eólica y otras fuentes de energía limpia.",
                "La energía renovable proviene de fuentes naturales que se reponen constantemente. A diferencia de los combustibles fósiles, la energía renovable produce pocas o ninguna emisión de gases de efecto invernadero.\n\n## Tipos de Energía Renovable\n\n### Energía Solar\nConvierte la luz solar en electricidad mediante paneles fotovoltaicos.\n\n### Energía Eólica\nUtiliza turbinas eólicas para generar electricidad.\n\n### Energía Hidroeléctrica\nGenera electricidad a partir del agua en movimiento.\n\n### Energía Geotérmica\nUtiliza el calor del interior de la Tierra.\n\n## Beneficios\n- Reduce las emisiones de carbono\n- Crea independencia energética\n- Menores costos a largo plazo\n- Sostenible y abundante",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1473341304170-971dccb5ac1e?w=600",
                6,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🌍 Cambio Climático",
                "Comprende las causas y efectos del cambio climático global.",
                "El cambio climático se refiere a cambios a largo plazo en las temperaturas y patrones climáticos. Aunque los factores naturales influyen, las actividades humanas han sido el principal impulsor desde el siglo XIX.\n\n## Principales Causas\n- Quema de combustibles fósiles\n- Deforestación\n- Agricultura industrial\n- Descomposición de residuos\n\n## Efectos que ya vemos\n- Aumento de temperaturas globales\n- Eventos climáticos extremos más frecuentes\n- Deshielo de glaciares y aumento del nivel del mar\n- Alteración de ecosistemas\n\n## Qué puedes hacer\n- Reduce el consumo de energía\n- Usa transporte público o bicicleta\n- Consume menos carne\n- Apoya las energías renovables\n- Planta árboles",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=600",
                7,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🗑️ Compostaje en Casa",
                "Convierte tus residuos de cocina en abono rico en nutrientes para tu jardín.",
                "El compostaje es la forma que tiene la naturaleza de reciclar residuos orgánicos y convertirlos en fertilizante valioso. Es fácil de empezar y marca una gran diferencia para el medio ambiente.\n\n## ¿Qué puedes compostar?\n- Restos de frutas y verduras\n- Posos de café y filtros\n- Cáscaras de huevo\n- Recortes de césped y hojas\n- Papel triturado\n\n## ¿Qué NO compostar?\n- Carne y productos lácteos\n- Alimentos grasosos\n- Plantas enfermas\n- Excrementos de mascotas\n\n## Cómo empezar\n1. Elige un contenedor o lugar para el compost\n2. Alterna capas de material verde y marrón\n3. Mantén la humedad adecuada\n4. Voltea regularmente para airear\n5. Espera 2 a 6 meses para tener compost listo",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1585336261022-680e295ce3fe?w=600",
                5,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🚰 Cómo Funciona el Tratamiento del Agua",
                "Infografía que muestra el recorrido del agua desde su origen hasta tu grifo.",
                "El tratamiento del agua es un proceso fascinante que garantiza que el agua que sale de tu grifo sea segura para beber.\n\nPaso 1: Captación - El agua se recolecta de ríos, lagos o embalses.\nPaso 2: Cribado - Se eliminan residuos grandes como ramas y hojas.\nPaso 3: Coagulación - Se añaden químicos para agrupar partículas pequeñas.\nPaso 4: Sedimentación - Los grupos se depositan en el fondo.\nPaso 5: Filtración - El agua pasa por filtros de arena y carbón.\nPaso 6: Desinfección - El cloro o luz UV eliminan microorganismos.\nPaso 7: Distribución - El agua limpia viaja por tuberías a tu hogar.\n\nDato curioso: ¡Una planta de tratamiento puede procesar millones de litros al día!",
                MaterialType.INFOGRAPHIC,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=600",
                3,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🏠 Consejos de Eficiencia Energética",
                "Reduce tus facturas de energía y tu huella de carbono con estos consejos prácticos.",
                "Hacer tu hogar más eficiente energéticamente es una de las mejores formas de ahorrar dinero y ayudar al medio ambiente.\n\n## Acciones Rápidas\n- Cambia a bombillas LED (usan 75% menos energía)\n- Desconecta aparatos electrónicos cuando no los uses\n- Usa un termostato programable\n- Sella corrientes de aire en puertas y ventanas\n\n## Inversiones Medias\n- Añade aislamiento al ático y paredes\n- Instala ventanas eficientes\n- Actualiza a electrodomésticos ENERGY STAR\n\n## Soluciones a Largo Plazo\n- Instala paneles solares\n- Cambia a una bomba de calor\n- Considera un techo verde\n\nUn hogar típico puede ahorrar 25-30% en facturas de energía implementando estas medidas.",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1508514177221-188b1cf16e9d?w=600",
                4,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "🌿 Biodiversidad y Tú",
                "Por qué la biodiversidad es importante y cómo protegerla en tu comunidad.",
                "La biodiversidad - la variedad de vida en la Tierra - es esencial para la salud de nuestro planeta y nuestro bienestar.\n\n## Por qué es importante la biodiversidad\n- Proporciona alimentos, agua limpia y medicinas\n- Apoya la polinización de cultivos\n- Regula el clima\n- Protege contra desastres naturales\n\n## Amenazas a la Biodiversidad\n- Destrucción de hábitats\n- Contaminación\n- Especies invasoras\n- Cambio climático\n- Sobreexplotación\n\n## Cómo puedes ayudar\n- Planta especies nativas en tu jardín\n- Evita los pesticidas\n- Apoya los esfuerzos de conservación locales\n- Reduce tu huella de carbono\n- Educa a otros sobre la biodiversidad",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=600",
                6,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "♻️ Crisis del Plástico",
                "Video que explica el impacto de la contaminación por plástico y las soluciones.",
                "La contaminación por plástico es uno de los problemas ambientales más urgentes de nuestro tiempo. Cada año, 8 millones de toneladas de plástico llegan a nuestros océanos.\n\n## El Problema\n- Solo el 9% del plástico producido ha sido reciclado\n- 12 millones de toneladas de plástico entran a los océanos anualmente\n- Para 2050, podría haber más plástico que peces en el mar\n- Se han encontrado microplásticos en agua potable, alimentos y sangre humana\n\n## Soluciones\n1. Reduce los plásticos de un solo uso\n2. Elige alternativas reutilizables\n3. Apoya leyes que prohíban plásticos innecesarios\n4. Participa en jornadas de limpieza\n5. Promueve la responsabilidad del productor\n\n## Qué puedes hacer hoy\nComienza por llevar una botella reutilizable, bolsa de compras y vaso para café.",
                MaterialType.VIDEO,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1611284446318-60a73ac1dbe2?w=600",
                8,
                "es"
        ));

        repository.save(new EducationalMaterial(
                "💡 Revolución de la Energía Verde",
                "Video que muestra las innovaciones en tecnología de energía renovable.",
                "La revolución de la energía verde está transformando la forma en que alimentamos nuestro mundo. Desde granjas solares flotantes hasta turbinas eólicas de nueva generación, la innovación se acelera.\n\n## Tecnologías Innovadoras\n- Celdas solares de perovskita (más baratas, más eficientes)\n- Turbinas eólicas flotantes (acceden a aguas más profundas)\n- Hidrógeno verde (combustible limpio para la industria)\n- Centrales eléctricas virtuales (baterías domésticas en red)\n\n## Progreso Global\n- La energía renovable ya es más barata que el carbón\n- Más del 30% de la electricidad global proviene de renovables\n- La energía solar crece más rápido que cualquier otra fuente\n- Muchos países buscan llegar al 100% renovable para 2050\n\n## El Futuro es Brillante\nLa transición a la energía renovable no solo es necesaria, ya está ocurriendo. Y tú puedes ser parte de ella.",
                MaterialType.VIDEO,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=600",
                10,
                "es"
        ));

        // ============ ENGLISH MATERIALS ============
        repository.save(new EducationalMaterial(
                "♻️ Basic Recycling",
                "Learn the fundamentals of recycling and how to properly separate your waste.",
                "Recycling is the process of converting waste materials into new materials and objects. This reduces the consumption of raw materials, energy usage, air and water pollution.\n\n## Why Recycle?\n- Conserves natural resources\n- Saves energy\n- Reduces waste in landfills\n- Creates jobs\n\n## What Can Be Recycled?\n- Paper and cardboard\n- Glass bottles and jars\n- Cans and aluminum foil\n- Plastic bottles and containers\n\n## Tips for Better Recycling\n1. Rinse containers before recycling\n2. Keep recyclables loose (not in bags)\n3. Check your local recycling guidelines",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b?w=600",
                5,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "💧 Water Saving at Home",
                "Simple ways to reduce water consumption in your daily life.",
                "Water is one of our most precious resources. With climate change affecting water availability, conserving it at home has never been more important.\n\n## Why Save Water?\n- Protect natural ecosystems\n- Reduce energy used in water treatment\n- Save money on utility bills\n- Ensure water for future generations\n\n## Easy Ways to Save Water\n- Fix leaking faucets\n- Take shorter showers\n- Turn off the tap while brushing your teeth\n- Use a broom instead of a hose to clean\n- Collect rainwater for plants\n\n## Did You Know?\nA single leaking faucet can waste over 3,000 liters of water per year!",
                MaterialType.TEXT,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1581092160562-40aa08e78837?w=600",
                4,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🔋 Renewable Energy",
                "An overview of solar, wind and other clean energy sources.",
                "Renewable energy comes from natural sources that are constantly replenished. Unlike fossil fuels, renewable energy produces little to no greenhouse gas emissions.\n\n## Types of Renewable Energy\n\n### Solar Energy\nConverts sunlight into electricity using photovoltaic panels.\n\n### Wind Energy\nUses wind turbines to generate electricity.\n\n### Hydroelectric Energy\nGenerates electricity from moving water.\n\n### Geothermal Energy\nUses heat from inside the Earth.\n\n## Benefits\n- Reduces carbon emissions\n- Creates energy independence\n- Lower costs in the long term\n- Sustainable and abundant",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1473341304170-971dccb5ac1e?w=600",
                6,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🌍 Climate Change",
                "Understand the causes and effects of global climate change.",
                "Climate change refers to long-term changes in temperatures and weather patterns. Although natural factors play a role, human activities have been the main driver since the 19th century.\n\n## Main Causes\n- Burning fossil fuels\n- Deforestation\n- Industrial agriculture\n- Waste decomposition\n\n## Effects We Already See\n- Rising global temperatures\n- More frequent extreme weather events\n- Melting glaciers and rising sea levels\n- Disrupted ecosystems\n\n## What You Can Do\n- Reduce energy consumption\n- Use public transport or bike\n- Eat less meat\n- Support renewable energy\n- Plant trees",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=600",
                7,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🗑️ Home Composting",
                "Turn your kitchen waste into nutrient-rich fertilizer for your garden.",
                "Composting is nature's way of recycling organic waste and turning it into valuable fertilizer. It's easy to start and makes a big difference for the environment.\n\n## What Can You Compost?\n- Fruit and vegetable scraps\n- Coffee grounds and filters\n- Eggshells\n- Grass clippings and leaves\n- Shredded paper\n\n## What NOT to Compost\n- Meat and dairy products\n- Greasy foods\n- Diseased plants\n- Pet waste\n\n## How to Start\n1. Choose a container or spot for composting\n2. Alternate layers of green and brown material\n3. Maintain proper moisture\n4. Turn regularly to aerate\n5. Wait 2 to 6 months for ready compost",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1585336261022-680e295ce3fe?w=600",
                5,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🚰 How Water Treatment Works",
                "Infographic showing water's journey from its source to your tap.",
                "Water treatment is a fascinating process that ensures the water coming out of your tap is safe to drink.\n\nStep 1: Collection - Water is collected from rivers, lakes or reservoirs.\nStep 2: Screening - Large debris like branches and leaves are removed.\nStep 3: Coagulation - Chemicals are added to clump small particles together.\nStep 4: Sedimentation - The clumps settle at the bottom.\nStep 5: Filtration - Water passes through sand and carbon filters.\nStep 6: Disinfection - Chlorine or UV light kills microorganisms.\nStep 7: Distribution - Clean water travels through pipes to your home.\n\nFun fact: A treatment plant can process millions of liters per day!",
                MaterialType.INFOGRAPHIC,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=600",
                3,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🏠 Energy Efficiency Tips",
                "Reduce your energy bills and carbon footprint with these practical tips.",
                "Making your home more energy efficient is one of the best ways to save money and help the environment.\n\n## Quick Actions\n- Switch to LED bulbs (use 75% less energy)\n- Unplug electronics when not in use\n- Use a programmable thermostat\n- Seal drafts around doors and windows\n\n## Medium Investments\n- Add attic and wall insulation\n- Install efficient windows\n- Upgrade to ENERGY STAR appliances\n\n## Long-term Solutions\n- Install solar panels\n- Switch to a heat pump\n- Consider a green roof\n\nA typical home can save 25-30% on energy bills by implementing these measures.",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1508514177221-188b1cf16e9d?w=600",
                4,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "🌿 Biodiversity and You",
                "Why biodiversity matters and how to protect it in your community.",
                "Biodiversity - the variety of life on Earth - is essential for the health of our planet and our well-being.\n\n## Why Biodiversity Matters\n- Provides food, clean water and medicine\n- Supports crop pollination\n- Regulates climate\n- Protects against natural disasters\n\n## Threats to Biodiversity\n- Habitat destruction\n- Pollution\n- Invasive species\n- Climate change\n- Overexploitation\n\n## How You Can Help\n- Plant native species in your garden\n- Avoid pesticides\n- Support local conservation efforts\n- Reduce your carbon footprint\n- Educate others about biodiversity",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=600",
                6,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "♻️ The Plastic Crisis",
                "A video explaining the impact of plastic pollution and the solutions.",
                "Plastic pollution is one of the most urgent environmental problems of our time. Every year, 8 million tons of plastic end up in our oceans.\n\n## The Problem\n- Only 9% of plastic produced has been recycled\n- 12 million tons of plastic enter the oceans annually\n- By 2050, there could be more plastic than fish in the sea\n- Microplastics have been found in drinking water, food and human blood\n\n## Solutions\n1. Reduce single-use plastics\n2. Choose reusable alternatives\n3. Support laws banning unnecessary plastics\n4. Participate in clean-up events\n5. Promote producer responsibility\n\n## What You Can Do Today\nStart by carrying a reusable water bottle, shopping bag and coffee cup.",
                MaterialType.VIDEO,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1611284446318-60a73ac1dbe2?w=600",
                8,
                "en"
        ));

        repository.save(new EducationalMaterial(
                "💡 Green Energy Revolution",
                "A video showcasing innovations in renewable energy technology.",
                "The green energy revolution is transforming how we power our world. From floating solar farms to next-generation wind turbines, innovation is accelerating.\n\n## Innovative Technologies\n- Perovskite solar cells (cheaper, more efficient)\n- Floating wind turbines (access deeper waters)\n- Green hydrogen (clean fuel for industry)\n- Virtual power plants (networked home batteries)\n\n## Global Progress\n- Renewable energy is now cheaper than coal\n- Over 30% of global electricity comes from renewables\n- Solar power is growing faster than any other source\n- Many countries aim for 100% renewable by 2050\n\n## The Future is Bright\nThe transition to renewable energy is not just necessary - it's already happening. And you can be part of it.",
                MaterialType.VIDEO,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=600",
                10,
                "en"
        ));
    }
}
