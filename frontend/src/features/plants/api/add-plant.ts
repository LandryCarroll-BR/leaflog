import { useMutation, useQueryClient } from '@tanstack/react-query';
import { z } from 'zod';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

import { getPlantsQueryOptions } from './get-plants';

export const addPlantInputSchema = z.object({
  name: z.string().min(1, 'Required'),
  species: z.string().min(1, 'Required'),
  wateringFrequencyAsString: z.coerce.number().min(1, 'Must be at least 1 day'),
  notes: z.string().optional(),
  lastWatered: z.coerce
    .date()
    .optional()
    .default(() => new Date()),
});

export type AddPlantInput = z.infer<typeof addPlantInputSchema>;

export const addPlant = ({ data }: { data: AddPlantInput }): Promise<Plant> => {
  return api.post(`/plants`, data);
};

type UseAddPlantOptions = {
  mutationConfig?: MutationConfig<typeof addPlant>;
};

export const useAddPlant = ({ mutationConfig }: UseAddPlantOptions = {}) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getPlantsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: addPlant,
  });
};
