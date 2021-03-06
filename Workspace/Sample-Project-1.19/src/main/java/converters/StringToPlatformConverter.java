
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.PlatformRepository;
import domain.Platform;

@Component
@Transactional
public class StringToPlatformConverter implements Converter<String, Platform> {

	@Autowired
	private PlatformRepository	platformRepository;


	@Override
	public Platform convert(final String text) {
		Platform result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.platformRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
