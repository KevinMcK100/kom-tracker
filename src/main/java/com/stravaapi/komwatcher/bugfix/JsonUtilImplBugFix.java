package com.stravaapi.komwatcher.bugfix;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import javastrava.api.v3.auth.ref.AuthorisationApprovalPrompt;
import javastrava.api.v3.auth.ref.AuthorisationResponseType;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaAthleteType;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaClubType;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.model.reference.StravaFrameType;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaPhotoSource;
import javastrava.api.v3.model.reference.StravaPhotoType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;
import javastrava.api.v3.model.reference.StravaSkillLevel;
import javastrava.api.v3.model.reference.StravaSportType;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.model.reference.StravaWorkoutType;
import javastrava.api.v3.model.webhook.reference.StravaSubscriptionAspectType;
import javastrava.api.v3.model.webhook.reference.StravaSubscriptionObjectType;
import javastrava.config.Messages;
import javastrava.config.StravaConfig;
import javastrava.json.JsonUtil;
import javastrava.json.exception.JsonSerialisationException;
import javastrava.json.impl.gson.serializer.ActivityTypeSerializer;
import javastrava.json.impl.gson.serializer.ActivityZoneTypeSerializer;
import javastrava.json.impl.gson.serializer.AgeGroupSerializer;
import javastrava.json.impl.gson.serializer.AthleteTypeSerializer;
import javastrava.json.impl.gson.serializer.AuthorisationApprovalPromptSerializer;
import javastrava.json.impl.gson.serializer.AuthorisationResponseTypeSerializer;
import javastrava.json.impl.gson.serializer.AuthorisationScopeSerializer;
import javastrava.json.impl.gson.serializer.ClimbCategorySerializer;
import javastrava.json.impl.gson.serializer.ClubTypeSerializer;
import javastrava.json.impl.gson.serializer.FollowerStateSerializer;
import javastrava.json.impl.gson.serializer.FrameTypeSerializer;
import javastrava.json.impl.gson.serializer.GenderSerializer;
import javastrava.json.impl.gson.serializer.LeaderboardDateRangeSerializer;
import javastrava.json.impl.gson.serializer.LocalDateSerializer;
import javastrava.json.impl.gson.serializer.MapPointSerializer;
import javastrava.json.impl.gson.serializer.MeasurementMethodSerializer;
import javastrava.json.impl.gson.serializer.PhotoTypeSerializer;
import javastrava.json.impl.gson.serializer.ResourceStateSerializer;
import javastrava.json.impl.gson.serializer.SegmentActivityTypeSerializer;
import javastrava.json.impl.gson.serializer.SegmentExplorerActivityTypeSerializer;
import javastrava.json.impl.gson.serializer.SkillLevelSerializer;
import javastrava.json.impl.gson.serializer.SportTypeSerializer;
import javastrava.json.impl.gson.serializer.StravaPhotoSourceSerializer;
import javastrava.json.impl.gson.serializer.StravaStreamSerializer;
import javastrava.json.impl.gson.serializer.StreamResolutionTypeSerializer;
import javastrava.json.impl.gson.serializer.StreamSeriesDownsamplingTypeSerializer;
import javastrava.json.impl.gson.serializer.StreamTypeSerializer;
import javastrava.json.impl.gson.serializer.SubscriptionAspectTypeSerializer;
import javastrava.json.impl.gson.serializer.SubscriptionObjectTypeSerializer;
import javastrava.json.impl.gson.serializer.WeightClassSerializer;
import javastrava.json.impl.gson.serializer.WorkoutTypeSerializer;

/**
 * <p>
 * GSON implementation of JSON utilities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class JsonUtilImplBugFix implements JsonUtil {
	/**
	 * GSON instance used for all JSON deserialisation and serialisation
	 */
	private final Gson gson;

	/**
	 * Default constructor
	 */
	public JsonUtilImplBugFix() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.setDateFormat(StravaConfig.DATE_FORMAT);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerBugFix());
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializerBugFix());
		gsonBuilder.registerTypeAdapter(AuthorisationApprovalPrompt.class, new AuthorisationApprovalPromptSerializer());
		gsonBuilder.registerTypeAdapter(AuthorisationResponseType.class, new AuthorisationResponseTypeSerializer());
		gsonBuilder.registerTypeAdapter(AuthorisationScope.class, new AuthorisationScopeSerializer());
		gsonBuilder.registerTypeAdapter(StravaActivityType.class, new ActivityTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaActivityZoneType.class, new ActivityZoneTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaAgeGroup.class, new AgeGroupSerializer());
		gsonBuilder.registerTypeAdapter(StravaAthleteType.class, new AthleteTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaClimbCategory.class, new ClimbCategorySerializer());
		gsonBuilder.registerTypeAdapter(StravaClubType.class, new ClubTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaFollowerState.class, new FollowerStateSerializer());
		gsonBuilder.registerTypeAdapter(StravaFrameType.class, new FrameTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaGender.class, new GenderSerializer());
		gsonBuilder.registerTypeAdapter(StravaLeaderboardDateRange.class, new LeaderboardDateRangeSerializer());
		gsonBuilder.registerTypeAdapter(StravaMapPoint.class, new MapPointSerializer());
		gsonBuilder.registerTypeAdapter(StravaMeasurementMethod.class, new MeasurementMethodSerializer());
		gsonBuilder.registerTypeAdapter(StravaPhotoSource.class, new StravaPhotoSourceSerializer());
		gsonBuilder.registerTypeAdapter(StravaPhotoType.class, new PhotoTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaResourceState.class, new ResourceStateSerializer());
		gsonBuilder.registerTypeAdapter(StravaSegmentActivityType.class, new SegmentActivityTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaSegmentExplorerActivityType.class, new SegmentExplorerActivityTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaSkillLevel.class, new SkillLevelSerializer());
		gsonBuilder.registerTypeAdapter(StravaSportType.class, new SportTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaStreamResolutionType.class, new StreamResolutionTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaStreamSeriesDownsamplingType.class, new StreamSeriesDownsamplingTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaStream.class, new StravaStreamSerializer());
		gsonBuilder.registerTypeAdapter(StravaStreamType.class, new StreamTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaSubscriptionAspectType.class, new SubscriptionAspectTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaSubscriptionObjectType.class, new SubscriptionObjectTypeSerializer());
		gsonBuilder.registerTypeAdapter(StravaWeightClass.class, new WeightClassSerializer());
		gsonBuilder.registerTypeAdapter(StravaWorkoutType.class, new WorkoutTypeSerializer());

		this.gson = gsonBuilder.create();
	}

	/**
	 * @see javastrava.json.JsonUtil#deserialise(java.io.InputStream, java.lang.Class)
	 */
	@Override
	public <T> T deserialise(final InputStream is, final Class<T> class1) throws JsonSerialisationException {
		if (is == null) {
			return null;
		}
		return this.gson.fromJson(new InputStreamReader(is), class1);
	}

	/**
	 * @see javastrava.json.JsonUtil#deserialise(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T deserialise(final String is, final Class<T> class1) throws JsonSerialisationException {
		try {
			return this.gson.fromJson(is, class1);
		} catch (final JsonParseException e) {
			throw new JsonSerialisationException(String.format(Messages.string("JsonUtilImpl.failedToDeserialiseString"), is, class1.getName()), e); //$NON-NLS-1$
		}
	}

	/**
	 * @return The GSON
	 */
	public Gson getGson() {
		return this.gson;
	}

	/**
	 * @see javastrava.json.JsonUtil#serialise(Object)
	 */
	@Override
	public <T> String serialise(final T object) throws JsonSerialisationException {
		return this.gson.toJson(object);
	}
}

